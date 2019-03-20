package com.sayaradz.views.fragments.newCars

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandRepository
import com.sayaradz.models.Offer
import com.sayaradz.models.OfferRepository
import com.sayaradz.viewModels.NewCarsViewModel
import com.sayaradz.views.activities.BrandsActivity
import com.sayaradz.views.activities.ModelsActivity
import com.sayaradz.views.activities.NewCarsDetailsActivity
import com.sayaradz.views.adapters.NewCarsBrandAdapter
import com.sayaradz.views.adapters.NewCarsOfferAdapter

class NewCarsMainFragment : Fragment(), NewCarsBrandAdapter.OnItemClickListener,
    NewCarsOfferAdapter.OnItemClickListener {

    private var itemArrayList: List<Offer>? = null
    var brandList: List<Brand>? = null
    private lateinit var newCarsBrandAdapter: NewCarsBrandAdapter
    private lateinit var newCarsOfferAdapter: NewCarsOfferAdapter

    private val viewModel: NewCarsViewModel = NewCarsViewModel()

    // RecyclerView
    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var newCarsOfferRecyclerView: RecyclerView

    private lateinit var moreBrands: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.new_cars_main_fragment, container, false)

        initData()

        initUI(view)

        initDataBinding()

        initActions(view)

        return view
    }


    //region Init Functions
    private fun initData() {

        // get data
        itemArrayList = OfferRepository.offerList

        brandList = BrandRepository.brandList
        //brandList = viewModel.getTopBrands()
    }

    private fun initUI(view: View) {

        // initToolbar();
        newCarsBrandAdapter = NewCarsBrandAdapter(brandList)

        newCarsOfferAdapter = NewCarsOfferAdapter(itemArrayList)

        brandRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        val mLayoutManager = LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        brandRecyclerView.layoutManager = mLayoutManager
        brandRecyclerView.itemAnimator = DefaultItemAnimator()
        brandRecyclerView.isNestedScrollingEnabled = false

        // get Item recycler view
        newCarsOfferRecyclerView = view.findViewById(R.id.photoRecyclerView)
        val mLayoutManagerForItems = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)

        newCarsOfferRecyclerView.layoutManager = mLayoutManagerForItems
        newCarsOfferRecyclerView.itemAnimator = DefaultItemAnimator()
        newCarsOfferRecyclerView.isNestedScrollingEnabled = false


        //linking the textView
        moreBrands = view.findViewById(R.id.moreBrandsButton)

    }

    private fun initDataBinding() {
        // bind wallpaperHome2CategoryAdapter to recycler
        brandRecyclerView.adapter = newCarsBrandAdapter

        // bind items
        newCarsOfferRecyclerView.adapter = newCarsOfferAdapter
    }

    private fun initActions(myView: View) {

        moreBrands.setOnClickListener {
            startActivity(Intent(myView.context, BrandsActivity::class.java))
        }

        newCarsBrandAdapter.setOnItemClickListener(this)
        newCarsOfferAdapter.setOnItemClickListener(this)


    }

    override fun onItemClick(view: View, obj: Brand, position: Int) {
        startActivity(Intent(view.context, ModelsActivity::class.java))
    }

    override fun onOfferItemClick(view: View, obj: Offer, position: Int) {
        startActivity(Intent(view.context, NewCarsDetailsActivity::class.java))
    }


    companion object {

        fun newInstance(): NewCarsMainFragment {
            return NewCarsMainFragment()
        }
    }


}
