package com.sayaradz.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandRepository
import com.sayaradz.views.adapters.BrandRecyclerViewAdapter

class BrandsActivity : AppCompatActivity(),BrandRecyclerViewAdapter.OnItemClickListener {


    var brandList: List<Brand>? = null
    private lateinit var itemAdapter: BrandRecyclerViewAdapter
    private lateinit var brandRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brands)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        brandRecyclerView = findViewById(R.id.brand_recycler_view)

    }

    override fun onStart() {
        super.onStart()

        brandList = BrandRepository.brandList
        itemAdapter = BrandRecyclerViewAdapter(brandList)
        val mLayoutManagerForItems = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

        brandRecyclerView.layoutManager = mLayoutManagerForItems
        brandRecyclerView.itemAnimator = DefaultItemAnimator()
        brandRecyclerView.isNestedScrollingEnabled = false

        brandRecyclerView.adapter = itemAdapter
        itemAdapter.setOnItemClickListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(view: View, obj: Brand, position: Int) {
        startActivity(Intent(this,ModelsActivity::class.java))
    }
}
