package com.sayaradz.views.fragments.newCars

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.models.Offer
import com.sayaradz.models.OfferRepository
import com.sayaradz.viewModels.BrandsViewModel
import com.sayaradz.views.activities.BrandsActivity
import com.sayaradz.views.activities.ModelsActivity
import com.sayaradz.views.activities.NewCarsDetailsActivity
import com.sayaradz.views.adapters.NewCarsBrandAdapter
import com.sayaradz.views.adapters.NewCarsOfferAdapter


class NewCarsMainFragment : Fragment(), NewCarsBrandAdapter.OnItemClickListener,
    NewCarsOfferAdapter.OnItemClickListener {

    private var itemArrayList: List<Offer>? = null

    private lateinit var newCarsBrandAdapter: NewCarsBrandAdapter
    private lateinit var newCarsOfferAdapter: NewCarsOfferAdapter

    private lateinit var mBrandsViewModel: BrandsViewModel

    // RecyclerView
    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var newCarsOfferRecyclerView: RecyclerView

    private lateinit var moreBrands: TextView
    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.new_cars_main_fragment, container, false)

        brandRecyclerView = view.findViewById(R.id.new_recycler_view)
        newCarsOfferRecyclerView = view.findViewById(R.id.old_recycler_view)
        //linking the textView
        moreBrands = view.findViewById(R.id.moreBrandsButton)
        noInternetTextView = view.findViewById(R.id.no_internet)
        contentNestedScrollView = view.findViewById(R.id.scroll_view)
        progressBar = view.findViewById(R.id.progressBar)

        itemArrayList = OfferRepository.offerList


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
                contentNestedScrollView.visibility = it
            }
        })

        mBrandsViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                newCarsBrandAdapter = NewCarsBrandAdapter(it.brands)
                newCarsOfferAdapter = NewCarsOfferAdapter(itemArrayList)
                brandRecyclerView.adapter = newCarsBrandAdapter
                newCarsOfferRecyclerView.adapter = newCarsOfferAdapter
                newCarsBrandAdapter.setOnItemClickListener(this@NewCarsMainFragment)
                newCarsOfferAdapter.setOnItemClickListener(this@NewCarsMainFragment)
            }
        })

        val mLayoutManager = LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        brandRecyclerView.layoutManager = mLayoutManager
        brandRecyclerView.itemAnimator = DefaultItemAnimator()
        brandRecyclerView.isNestedScrollingEnabled = false

        // get Item recycler view
        val mLayoutManagerForItems = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        newCarsOfferRecyclerView.layoutManager = mLayoutManagerForItems
        newCarsOfferRecyclerView.itemAnimator = DefaultItemAnimator()
        newCarsOfferRecyclerView.isNestedScrollingEnabled = false


        moreBrands.setOnClickListener {
            startActivity(Intent(this.context, BrandsActivity::class.java))
        }

        return view
    }

    override fun onItemClick(view: View, obj: Brand, position: Int) {
        val intent = Intent(view.context, ModelsActivity::class.java)
        intent.putExtra("brandId",obj.id)
        intent.putExtra("brandLogo",obj.logo)
        intent.putExtra("brandName",obj.name)
        startActivity(intent)
    }

    override fun onOfferItemClick(view: View, obj: Offer, position: Int) {
        startActivity(Intent(view.context, NewCarsDetailsActivity::class.java))
    }

}