package com.sayaradz.views.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.CarCompare
import com.sayaradz.models.Option
import com.sayaradz.models.Version
import com.sayaradz.views.adapters.CompareOptionsRecyclerViewAdapter
import com.sayaradz.viewModels.TwoVersionsViewModel
import kotlinx.android.synthetic.main.activity_compare.*


class CompareActivity : AppCompatActivity() {

    private lateinit var comparingRecyclerView: RecyclerView
    private lateinit var comparingRecyclerViewAdapter: CompareOptionsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: CardView
    private lateinit var progressBar: ProgressBar

    private lateinit var mVersion1Name: TextView
    private lateinit var mVersion2Name: TextView
    private lateinit var mVersion1ImageView: ImageView
    private lateinit var mVersion2ImageView: ImageView

    private lateinit var mTwoVersionsViewModel: TwoVersionsViewModel

    private lateinit var version1: Version
    private lateinit var version2: Version
    private lateinit var carCompareList: List<CarCompare>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        comparingRecyclerView = comparing_recycler_view
        this.progressBar = comparing_progressBar
        this.noInternetTextView = comparing_no_internet
        this.contentView = comparing_content_view
        this.mVersion1ImageView = version_1_image
        this.mVersion2ImageView = version_2_image
        this.mVersion1Name = version_1_name
        this.mVersion2Name = version_2_name

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = ""
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        version1 = this.intent.getSerializableExtra("firstVersion") as Version
        version2 = this.intent.getSerializableExtra("secondVersion") as Version


        mTwoVersionsViewModel = ViewModelProviders.of(
            this,
            twoVersionsViewModelFactory {
                TwoVersionsViewModel(
                    version1.id.toString(),
                    version2.id.toString()
                )
            }
        ).get(TwoVersionsViewModel::class.java)

        mTwoVersionsViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentView.visibility = it
            }
        })

        mTwoVersionsViewModel.loadingVisibility.observe(this, Observer { loading ->
            loading?.let {
                this.progressBar.visibility = it
            }
        })

        mTwoVersionsViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })

        mTwoVersionsViewModel.versionLiveData.observe(this, Observer { v ->
            v?.let {
                carCompareList = compare(it.version1, it.version2)
                this.mVersion1Name.text = version1.name
                this.mVersion2Name.text = version2.name
                Glide.with(this)
                    .load(version1.image)
                    .into(this.mVersion1ImageView)
                Glide.with(this)
                    .load(version2.image)
                    .into(this.mVersion2ImageView)
                compareTextView.visibility = View.VISIBLE
                comparingRecyclerViewAdapter = CompareOptionsRecyclerViewAdapter(carCompareList)
                comparingRecyclerView.adapter = comparingRecyclerViewAdapter

            }
        })

    }


    override fun onStart() {
        super.onStart()


        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        comparingRecyclerView.layoutManager = mLayoutManager
        comparingRecyclerView.itemAnimator = DefaultItemAnimator()
        comparingRecyclerView.isNestedScrollingEnabled = false

    }

    private fun compare(version1: Version?, version2: Version?): List<CarCompare> {

        val carCompareList = ArrayList<CarCompare>()
        var exists: Boolean
        var car: CarCompare
        var colorCar: CarCompare
        if (version1 != null) {
            for (option: Option in version1.options!!) {
                car = CarCompare(option.code, option, null, ArrayList(), ArrayList())
                carCompareList.add(car)
            }
        }

        val listSize = carCompareList.size

        if (version2 != null) {
            for (option: Option in version2.options!!) {
                exists = false
                for (i in 0 until listSize) {
                    if (carCompareList[i].name == option.code) {
                        carCompareList[i].vOption2 = option
                        exists = true
                    }
                }
                if (!exists) carCompareList.add(CarCompare(option.code, null, option, ArrayList(), ArrayList()))
            }

        }

        if (version1 != null && version2 != null) {

            if (version1.colors != null && version2.colors != null && version1.colors.isNotEmpty() && version2.colors.isNotEmpty()) {
                colorCar = CarCompare(getString(R.string.Colors), null, null, version1.colors, version2.colors)
                carCompareList.add(colorCar)
            } else if (version1.colors != null && version1.colors.isNotEmpty()) {
                colorCar =
                    CarCompare(getString(R.string.Colors), null, null, version1.colors, ArrayList())
                carCompareList.add(colorCar)
            } else if (version2.colors != null && version2.colors.isNotEmpty()) {
                colorCar =
                    CarCompare(getString(R.string.Colors), null, null, ArrayList(), version2.colors)
                carCompareList.add(colorCar)
            }

        }


        return carCompareList
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    private inline fun <VM : ViewModel> twoVersionsViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}
