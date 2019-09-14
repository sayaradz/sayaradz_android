package com.sayaradz.views.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.viewModels.VersionViewModel
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.OptionsRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_new_cars_details.*

class NewCarsDetailsActivity : AppCompatActivity() {

    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private lateinit var optionsRecyclerViewAdapter: OptionsRecyclerViewAdapter

    private lateinit var mVersionViewModel: VersionViewModel

    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var optionsRecyclerView: RecyclerView

    private lateinit var versionImage: ImageView
    private lateinit var modelTextView: TextView
    private lateinit var versionTextView: TextView
    private lateinit var brandLogo: ImageView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cars_details)


        val actionbar = supportActionBar
        actionbar!!.title = this.intent.getStringExtra("versionName")
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        contentView = content_view_details
        noInternetTextView = no_internet_details
        progressBar = progress_bar_details
        versionImage = imageView
        colorsRecyclerView = colors_recycler_view
        optionsRecyclerView = options_recycler_view
        //buyButton = command_button
        modelTextView = model_details_text
        versionTextView = version_details_text
        brandLogo = brand_logo

        contentView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()

        mVersionViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory { VersionViewModel(this.intent.getStringExtra("versionId")) }
        ).get(VersionViewModel::class.java)

        mVersionViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mVersionViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mVersionViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentView.visibility = it
            }
        })

        mVersionViewModel.versionLiveData.observe(this, Observer { version ->
            version?.let {

                versionTextView.text = it.name
                modelTextView.text = this.intent.getStringExtra("modelName")

                Glide.with(brandLogo.context)
                    .load(this.intent.getStringExtra("brandLogo"))
                    .into(brandLogo)

                colorsRecyclerViewAdapter = ColorsRecyclerViewAdapter(it.colors)
                optionsRecyclerViewAdapter = OptionsRecyclerViewAdapter(it.options)

                colorsRecyclerView.adapter = colorsRecyclerViewAdapter
                optionsRecyclerView.adapter = optionsRecyclerViewAdapter

                Glide.with(versionImage.context)
                    .load(it.image)
                    .into(versionImage)

            }
        })

        val mLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        colorsRecyclerView.layoutManager = mLayoutManager
        colorsRecyclerView.itemAnimator = DefaultItemAnimator()
        colorsRecyclerView.isNestedScrollingEnabled = false


        val vLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        optionsRecyclerView.layoutManager = vLayoutManager
        optionsRecyclerView.itemAnimator = DefaultItemAnimator()
        optionsRecyclerView.isNestedScrollingEnabled = false

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }


    private inline fun <VM : ViewModel> versionViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}
