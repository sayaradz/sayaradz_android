package com.sayaradz.views.activities

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.viewModels.FollowedModelViewModel
import com.sayaradz.viewModels.FollowedVersionViewModel
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_notifs_list.*


class FollowedListActivity : AppCompatActivity() {

    private lateinit var mFollowedVersionViewModel: FollowedVersionViewModel
    private lateinit var mFollowedModelViewModel: FollowedModelViewModel

    // RecyclerView
    private lateinit var notifsRecyclerView: RecyclerView
    private lateinit var followedVersionsRecyclerViewAdapter: VersionsRecyclerViewAdapter
    private lateinit var followedModelsRecyclerViewAdapter: ModelsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followed_list)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Mes Notifications"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        notifsRecyclerView = notifs_recycler_view

        contentNestedScrollView = content_view_notif
        noInternetTextView = no_internet_notif
        progressBar = progress_bar_notif

    }

    override fun onResume() {
        super.onResume()

        //TODO put the right id in place
        mFollowedVersionViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { FollowedVersionViewModel("test") }
        ).get(FollowedVersionViewModel::class.java)

        mFollowedVersionViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mFollowedVersionViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mFollowedVersionViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mFollowedVersionViewModel.versionLiveData.observe(this, Observer { Response ->
            Response?.let {
                followedVersionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(it)
                notifsRecyclerView.adapter = followedVersionsRecyclerViewAdapter
            }
        })

        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        notifsRecyclerView.layoutManager = mLayoutManager
        notifsRecyclerView.itemAnimator = DefaultItemAnimator()
        notifsRecyclerView.isNestedScrollingEnabled = false

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


}