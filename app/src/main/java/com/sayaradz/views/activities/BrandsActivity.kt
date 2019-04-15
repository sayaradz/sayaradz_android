package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.viewModels.BrandViewModel
import com.sayaradz.views.adapters.BrandRecyclerViewAdapter

class BrandsActivity : AppCompatActivity(), BrandRecyclerViewAdapter.OnItemClickListener {


    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: LinearLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var itemAdapter: BrandRecyclerViewAdapter

    private lateinit var mBrandViewModel: BrandViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        brandRecyclerView = findViewById(R.id.comparing_recycler_view)

        noInternetTextView = findViewById(R.id.no_internet)
        contentView = findViewById(R.id.content_view)
        progressBar = findViewById(R.id.progressBar)

    }

    override fun onStart() {
        super.onStart()

        mBrandViewModel = ViewModelProviders.of(this).get(BrandViewModel::class.java)
        mBrandViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mBrandViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mBrandViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentView.visibility = it
            }
        })

        mBrandViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                itemAdapter = BrandRecyclerViewAdapter(it.brands)
                brandRecyclerView.adapter = itemAdapter
                itemAdapter.setOnItemClickListener(this@BrandsActivity)
            }
        })

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


}
