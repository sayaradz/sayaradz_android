package com.sayaradz.views.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sayaradz.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException


/**
 * A login screen that offers login via google/facebook.
 **/

class LoginActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 555
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    lateinit var progressBar: ProgressBar
    lateinit var callbackManager: CallbackManager


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        setupUI()
    }

    private fun setupUI() {
        progressBar = this.progressBar1

        button_facebook_login_copy.setOnClickListener {
            buttonFacebookLogin.performClick()
        }

        buttonFacebookLogin.setOnClickListener {
            signInFacebook()
            val currentUser = firebaseAuth.currentUser
            Log.i("taggggg", currentUser.toString())
        }

        google_btn.setOnClickListener {
            configureGoogleSignIn()
            signInGoogle()
            progressBar.visibility = View.VISIBLE
        }
    }

    // ------------------------------ FACEBOOK LOGIN -------------------------------//
    private fun signInFacebook() {

        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin.setReadPermissions("email", "public_profile")

        buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d("", "facebook:onSuccess:$loginResult")

                handleFacebookAccessToken(loginResult.accessToken)
                GraphLoginRequest(loginResult.accessToken)

            }

            // Method to access Facebook User Data.
            protected fun GraphLoginRequest(accessToken: AccessToken) {
                val graphRequest = GraphRequest.newMeRequest(
                    accessToken
                ) { jsonObject, graphResponse ->
                    try {

                        Log.e("log", jsonObject.toString())
                        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
                        val editor = prefs.edit()
                        editor.putString("fullName", jsonObject.getString("name"))
                        editor.putString(
                            "profilePicLink",
                            "https://graph.facebook.com/${jsonObject.getString("id")}/picture?type=large"
                        )
                        editor.putString("address", jsonObject.getString("email"))
                        editor.apply()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                val bundle = Bundle()
                bundle.putString(
                    "fields",
                    "id,name,link,email,gender,last_name,first_name,locale,timezone,updated_time,verified"
                )
                graphRequest.parameters = bundle
                graphRequest.executeAsync()

            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "facebook:onCancel!", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "facebook:onCancel")
                //-------------------------------------------------------------------------------------------//

            }

            override fun onError(error: FacebookException) {
                Toast.makeText(this@LoginActivity, "facebook:onError!", Toast.LENGTH_SHORT).show()
                Log.d("TAG", "facebook:onError", error)
                //-------------------------------------------------------------------------------------------//
            }
        })

    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("tag", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    startActivity(MainActivity.getLaunchIntent(this))
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }

            }
    }

    // ------------------------------ END FACEBOOK LOGIN -------------------------------//

    // --------------------------- START GOOGLE LOGIN -------------------------------//

    //TODO fix google auth
    private fun configureGoogleSignIn() {

        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)

    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            val currentUser = firebaseAuth.currentUser


            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            Log.i("taggggg", task.result.toString())
            try {

                progressBar.visibility = View.GONE
                // Google Sign In was successful, authenticate with Firebase
                val acct = task.getResult(ApiException::class.java)
                if (acct != null) {
                    updateUser(acct)
                }
                startActivity(MainActivity.getLaunchIntent(this))
                firebaseAuthWithGoogle(acct!!)

            } catch (e: ApiException) {
                progressBar.visibility = View.GONE

                // Google Sign In failed, update UI appropriately
                Log.w("Log", "Google sign in failed", e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateUser(acct: GoogleSignInAccount) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editor = prefs.edit()

        if (acct != null) {
            editor.putString("fullName", acct.displayName)
            editor.putString("profilePicLink", acct.photoUrl.toString())
            editor.putString("address", acct.email)
            editor.apply()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = firebaseAuth.currentUser

//                Toast.makeText(this, "${user?.email} -  ${user?.uid}", Toast.LENGTH_LONG).show()

                startActivity(MainActivity.getLaunchIntent(this))

            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    // ------------------------------ END GOOGLE LOGIN -------------------------------//

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            updateUser(account)
        }
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startActivity(MainActivity.getLaunchIntent(this))
            finish()
        }
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


}