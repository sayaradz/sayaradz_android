package com.sayaradz.views.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sayaradz.R
import com.sayaradz.views.fragments.home.HomeFragment
import com.sayaradz.views.fragments.myAccount.AccountFragment
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity(),
    HomeFragment.OnFragmentInteractionListener,
    AccountFragment.OnFragmentInteractionListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        try {
            user_mail.text = auth.currentUser?.email
        } catch (e: Exception) {

        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        findViewById<BottomNavigationView>(R.id.bottom_nav)?.let { bottomNavView ->
            NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
        }


    }


    private fun signOut() {
        auth.signOut()
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        prefs.edit().clear().apply()
        startActivity(LoginActivity.getLaunchIntent(this))
    }

    override fun onFragmentInteraction(uri: Uri) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onUpdateUserData(fullName: TextView, address: TextView, profilePic: ImageView) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        fullName.text = prefs.getString("fullName", "Jean Doe")
        address.text = prefs.getString("address", "xyz@gmail.com")
        Glide.with(this)
            .load(prefs.getString("profilePicLink", "test"))
            .into(profilePic)
    }

    override fun onSignOutPressed() {
        signOut()
    }

    override fun onNotifPressed() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAnnouncesPressed() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPaymentPressed() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFavoritesPressed() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

}
