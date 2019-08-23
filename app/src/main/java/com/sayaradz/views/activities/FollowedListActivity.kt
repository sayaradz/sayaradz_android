package com.sayaradz.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sayaradz.R
import com.sayaradz.views.adapters.MyPagerAdapter
import kotlinx.android.synthetic.main.activity_followed_list.*


class FollowedListActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followed_list)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Mes Favoris"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager, this)
        view_pager.adapter = fragmentAdapter

        tab_layout.setupWithViewPager(view_pager)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}