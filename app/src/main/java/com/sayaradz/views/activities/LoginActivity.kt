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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sayaradz.R
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException


/**
 * A login screen that offers login via google/facebook.
 **/

class LoginActivity : AppCompatActivity() {

    private val rcSignIn: Int = 555
    private var fb: Int = 1
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    private lateinit var progressBar: ProgressBar
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        mAuth = FirebaseAuth.getInstance()

        setupUI()
    }

    private fun setupUI() {
        progressBar = this.progressBar1

        button_facebook_login_copy.setOnClickListener {
            buttonFacebookLogin.performClick()
        }

        buttonFacebookLogin.setOnClickListener {
            signInFacebook()
            progressBar.visibility = View.VISIBLE
            loading_background.visibility = View.VISIBLE
        }

        configureGoogleSignIn()

        google_btn.setOnClickListener {
            signInGoogle()
            progressBar.visibility = View.VISIBLE
            loading_background.visibility = View.VISIBLE
        }

    }

    // ------------------------------ FACEBOOK LOGIN -------------------------------//
    private fun signInFacebook() {

        fb = 0
        callbackManager = CallbackManager.Factory.create()

        buttonFacebookLogin.setReadPermissions("email", "public_profile")

        buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d("", "facebook:onSuccess:$loginResult")

                handleFacebookAccessToken(loginResult.accessToken)
                graphLoginRequest(loginResult.accessToken)

            }

            override fun onCancel() {
                progressBar.visibility = View.GONE
                loading_background.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "facebook Login annulé", Toast.LENGTH_SHORT).show()

            }

            override fun onError(error: FacebookException) {
                progressBar.visibility = View.GONE
                loading_background.visibility = View.GONE
                Toast.makeText(this@LoginActivity, "facebook Login erreur!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    // Method to access Facebook User Data.
    private fun graphLoginRequest(accessToken: AccessToken) {
        val graphRequest = GraphRequest.newMeRequest(
            accessToken
        ) { jsonObject, _ ->
            try {

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

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("tag", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(MainActivity.getLaunchIntent(this))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()

                }

            }
    }

    // ------------------------------ END FACEBOOK LOGIN -------------------------------//

    // --------------------------- START GOOGLE LOGIN -------------------------------//

    private fun configureGoogleSignIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun signInGoogle() {
        fb = 1
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, rcSignIn)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (fb == 0) callbackManager.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == rcSignIn) {
            Log.e("Tag", "Discover: " + data!!.getParcelableExtra("googleSignInStatus"))
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                progressBar.visibility = View.GONE
                loading_background.visibility = View.GONE
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e)
                Toast.makeText(this, "Auth Failed", Toast.LENGTH_LONG).show()
                // ...
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("", "signInWithCredential:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Auth Failed", Toast.LENGTH_LONG).show()
                    updateUI(null)
                }

                // ...
            }
    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            val editor = prefs.edit()

            editor.putString("fullName", user.displayName)
            editor.putString("profilePicLink", user.photoUrl.toString())
            editor.putString("address", user.email)
            editor.putString("id", user.uid)
            editor.apply()

            progressBar.visibility = View.GONE
            loading_background.visibility = View.GONE
            startActivity(MainActivity.getLaunchIntent(this))
            finish()
        }
    }

    // ------------------------------ END GOOGLE LOGIN -------------------------------//

    override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }


}