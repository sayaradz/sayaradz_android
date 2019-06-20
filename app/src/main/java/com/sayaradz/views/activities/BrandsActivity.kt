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
import com.sayaradz.viewModels.BrandsViewModel
import com.sayaradz.views.adapters.BrandRecyclerViewAdapter

class BrandsActivity : AppCompatActivity(), BrandRecyclerViewAdapter.OnItemClickListener {


    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: LinearLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var itemAdapter: BrandRecyclerViewAdapter

    private lateinit var mBrandsViewModel: BrandsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        brandRecyclerView = findViewById(R.id.comparing_recycler_view)

        noInternetTextView = findViewById(R.id.no_internet)
        contentView = findViewById(R.id.content_view)
        progressBar = findViewById(R.id.progressBar)

    }

    override fun onStart() {
        super.onStart()

        mBrandsViewModel = ViewModelProviders.of(this).get(BrandsViewModel::class.java)
        mBrandsViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mBrandsViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mBrandsViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentView.visibility = it
            }
        })

        mBrandsViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
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
        val intent = Intent(view.context, ModelsActivity::class.java)
        intent.putExtra("brandId",obj.id)
        intent.putExtra("brandLogo",obj.logo)
        intent.putExtra("brandName",obj.name)
        startActivity(intent)
    }


}
