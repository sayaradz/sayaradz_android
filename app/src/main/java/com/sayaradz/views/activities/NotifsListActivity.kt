package com.sayaradz.views.activities

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.viewModels.NotificationViewModel
import com.sayaradz.views.adapters.NotificationsRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_notifs_list.*


class NotifsListActivity : AppCompatActivity() {

    private lateinit var mNotificationViewModel: NotificationViewModel

    // RecyclerView
    private lateinit var notifsRecyclerView: RecyclerView
    private lateinit var notifsRecyclerViewAdapter: NotificationsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifs_list)


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

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val id = prefs.getString("id", "")!!

        mNotificationViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { NotificationViewModel(id) }
        ).get(NotificationViewModel::class.java)

        mNotificationViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mNotificationViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mNotificationViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mNotificationViewModel.modelLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                notifsRecyclerViewAdapter = NotificationsRecyclerViewAdapter(it)
                notifsRecyclerView.adapter = notifsRecyclerViewAdapter

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
