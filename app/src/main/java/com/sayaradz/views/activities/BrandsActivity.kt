package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.apiClient.ApiService
import com.sayaradz.views.adapters.BrandRecyclerViewAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BrandsActivity : AppCompatActivity(), BrandRecyclerViewAdapter.OnItemClickListener {


    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: LinearLayout
    private lateinit var progressBar: ProgressBar

    var brandList: List<Brand>? = null
    private lateinit var itemAdapter: BrandRecyclerViewAdapter
    private lateinit var brandObserver: Observer<BrandsResponse>
    val TAG = "Brands Activity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        brandRecyclerView = findViewById(R.id.brand_recycler_view)

        noInternetTextView = findViewById(R.id.no_internet)
        contentView = findViewById(R.id.content_view)
        progressBar = findViewById(R.id.progressBar)

    }

    override fun onStart() {
        super.onStart()

        brandObserver = getBrandssObserver()

        ApiService.invoke().getBrands(10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(brandObserver)

        val mLayoutManagerForItems = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        brandRecyclerView.layoutManager = mLayoutManagerForItems
        brandRecyclerView.itemAnimator = DefaultItemAnimator()
        brandRecyclerView.isNestedScrollingEnabled = false

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(view: View, obj: Brand, position: Int) {
        startActivity(Intent(this, ModelsActivity::class.java))
    }

    private fun getBrandssObserver(): Observer<BrandsResponse> {
        return object : Observer<BrandsResponse> {
            override fun onSubscribe(d: Disposable) {
                Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: BrandsResponse) {
                brandList = s.brands
            }

            override fun onError(e: Throwable) {
                progressBar.visibility = View.GONE
                noInternetTextView.visibility = View.VISIBLE
            }

            override fun onComplete() {
                progressBar.visibility = View.GONE
                contentView.visibility = View.VISIBLE
                itemAdapter = BrandRecyclerViewAdapter(brandList)
                brandRecyclerView.adapter = itemAdapter
                itemAdapter.setOnItemClickListener(this@BrandsActivity)

            }
        }
    }

}
