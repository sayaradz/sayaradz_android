package com.sayaradz.views.fragments.newCars

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.*
import com.sayaradz.R
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.Offer
import com.sayaradz.models.OfferRepository
import com.sayaradz.models.apiClient.ApiService
import com.sayaradz.viewModels.NewCarsViewModel
import com.sayaradz.views.activities.BrandsActivity
import com.sayaradz.views.activities.ModelsActivity
import com.sayaradz.views.activities.NewCarsDetailsActivity
import com.sayaradz.views.adapters.NewCarsBrandAdapter
import com.sayaradz.views.adapters.NewCarsOfferAdapter
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class NewCarsMainFragment : Fragment(), NewCarsBrandAdapter.OnItemClickListener,
    NewCarsOfferAdapter.OnItemClickListener {

    private var itemArrayList: List<Offer>? = null
    private lateinit var brandList: List<Brand>

    var TAG = "NewCarsMainFragment: "

    private lateinit var brandObserver: Observer<BrandsResponse>
    private lateinit var newCarsBrandAdapter: NewCarsBrandAdapter
    private lateinit var newCarsOfferAdapter: NewCarsOfferAdapter

    private val viewModel: NewCarsViewModel = NewCarsViewModel()

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

        brandObserver = getBrandssObserver()

        ApiService.invoke().getBrands(10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(brandObserver)

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
        startActivity(Intent(view.context, ModelsActivity::class.java))
    }

    override fun onOfferItemClick(view: View, obj: Offer, position: Int) {
        startActivity(Intent(view.context, NewCarsDetailsActivity::class.java))
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
                contentNestedScrollView.visibility = View.VISIBLE
                newCarsBrandAdapter = NewCarsBrandAdapter(brandList)
                newCarsOfferAdapter = NewCarsOfferAdapter(itemArrayList)
                brandRecyclerView.adapter = newCarsBrandAdapter
                newCarsOfferRecyclerView.adapter = newCarsOfferAdapter
                newCarsBrandAdapter.setOnItemClickListener(this@NewCarsMainFragment)
                newCarsOfferAdapter.setOnItemClickListener(this@NewCarsMainFragment)
            }
        }
    }


    companion object {

        fun newInstance(): NewCarsMainFragment {
            return NewCarsMainFragment()
        }
    }


}