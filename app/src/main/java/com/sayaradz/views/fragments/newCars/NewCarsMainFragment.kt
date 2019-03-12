package com.sayaradz.views.fragments.newCars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.sayaradz.R
import com.sayaradz.viewModels.NewCarsViewModel
import com.sayaradz.views.adapters.NewCarsBrandAdapter
import com.sayaradz.views.adapters.NewCarsOfferAdapter
import com.sayaradz.models.*

class NewCarsMainFragment : Fragment() {

    private var itemArrayList: List<Offer>? = null
    var brandList: List<Brand>? = null
    private lateinit var newCarsBrandAdapter: NewCarsBrandAdapter
    private lateinit var itemAdapter: NewCarsOfferAdapter

    private val viewModel: NewCarsViewModel = NewCarsViewModel()

    private lateinit var viewAllCategoryTextView: TextView

    // RecyclerView
    private lateinit var brandRecyclerView: RecyclerView
    private lateinit var photoRecyclerView: RecyclerView


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

        itemAdapter = NewCarsOfferAdapter(itemArrayList)

        brandRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        val mLayoutManager = LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        brandRecyclerView.layoutManager = mLayoutManager
        brandRecyclerView.itemAnimator = DefaultItemAnimator()
        brandRecyclerView.isNestedScrollingEnabled = false

        // get Item recycler view
        photoRecyclerView = view.findViewById(R.id.photoRecyclerView)
        val mLayoutManagerForItems = StaggeredGridLayoutManager(2,GridLayoutManager.VERTICAL)

        photoRecyclerView.layoutManager = mLayoutManagerForItems
        photoRecyclerView.itemAnimator = DefaultItemAnimator()
        photoRecyclerView.isNestedScrollingEnabled = false

        viewAllCategoryTextView = view.findViewById(R.id.moreBrandsButton)


    }

    private fun initDataBinding() {
        // bind wallpaperHome2CategoryAdapter to recycler
        brandRecyclerView.adapter = newCarsBrandAdapter

        // bind items
        photoRecyclerView.adapter = itemAdapter
    }

    private fun initActions(myView: View) {
        /*newCarsBrandAdapter.setOnItemClickListener { view: View, obj, position ->
            Toast.makeText(
                myView.context.applicationContext,
                "Clicked " + obj.name,
                Toast.LENGTH_SHORT
            ).show()
        }

        itemAdapter.setOnItemClickListener { view: View, obj, position ->
            Toast.makeText(
                myView.context.applicationContext,
                "Selected : " + obj.imageName,
                Toast.LENGTH_SHORT
            ).show()
        }*/

        viewAllCategoryTextView.setOnClickListener {
            Toast.makeText(
                myView.context.applicationContext,
                "Clicked View All Categories.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }


    companion object {

        fun newInstance(): NewCarsMainFragment {
            return NewCarsMainFragment()
        }
    }


}
