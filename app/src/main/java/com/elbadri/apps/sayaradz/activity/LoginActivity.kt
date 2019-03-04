package com.elbadri.apps.sayaradz.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elbadri.apps.sayaradz.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login2.*


/**
 * A login screen that offers login via google/facebook.
 */

class LoginActivity : AppCompatActivity() {
    val RC_SIGN_IN: Int = 555
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    lateinit var progressBar: ProgressBar
    lateinit var callbackManager: CallbackManager


    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.elbadri.apps.sayaradz.R.layout.activity_login2)
        firebaseAuth = FirebaseAuth.getInstance()
        setupUI()
    }

    private fun setupUI() {
        progressBar = this.progressBar1

        buttonFacebookLogin.setOnClickListener {
            signInFacebook()
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

        buttonFacebookLogin.setReadPermissions("email", "public_profile", "user_friends")

        buttonFacebookLogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {

            override fun onSuccess(loginResult: LoginResult) {
                Log.d("", "facebook:onSuccess:$loginResult")
                Toast.makeText(this@LoginActivity, "onSuccess!", Toast.LENGTH_SHORT).show()

                handleFacebookAccessToken(loginResult.accessToken)

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

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
//                  Toast.makeText(this@LoginActivity, "is loogin!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
//                startActivity(MainActivity.getLaunchIntent(this))
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                progressBar.visibility = View.GONE

                // Google Sign In failed, update UI appropriately
                Log.w("Log", "Google sign in failed", e)
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
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