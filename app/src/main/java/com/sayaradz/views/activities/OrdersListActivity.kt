package com.sayaradz.views.activities

import android.os.Bundle
import android.util.Log
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
import com.sayaradz.viewModels.OrdersViewModel
import com.sayaradz.views.adapters.OrdersRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_orders_list.*


class OrdersListActivity : AppCompatActivity() {

    private lateinit var mOrdersViewModel: OrdersViewModel

    // RecyclerView
    private lateinit var ordersRecyclerView: RecyclerView
    private lateinit var ordersRecyclerViewAdapter: OrdersRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders_list)


        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Mes Commandes"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        ordersRecyclerView = order_recycler_view

        contentNestedScrollView = content_view_order
        noInternetTextView = no_internet_order
        progressBar = progress_bar_order

    }

    override fun onResume() {
        super.onResume()

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val id = prefs.getString("id", "")!!

        mOrdersViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { OrdersViewModel(id) }
        ).get(OrdersViewModel::class.java)

        mOrdersViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mOrdersViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mOrdersViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mOrdersViewModel.versionLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                Log.e("testttttt", it.orders.toString())
                ordersRecyclerViewAdapter = OrdersRecyclerViewAdapter(it.orders)
                ordersRecyclerView.adapter = ordersRecyclerViewAdapter

            }
        })

        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        ordersRecyclerView.layoutManager = mLayoutManager
        ordersRecyclerView.itemAnimator = DefaultItemAnimator()
        ordersRecyclerView.isNestedScrollingEnabled = false

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
