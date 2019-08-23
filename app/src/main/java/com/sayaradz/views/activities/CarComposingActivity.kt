package com.sayaradz.views.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.viewModels.AvailableColorsAndOptionsViewModel
import com.sayaradz.views.MyItemDetailsLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.SelectedOptionsRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_car_composing.*

class CarComposingActivity : AppCompatActivity() {

    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private lateinit var optionsRecyclerViewAdapter: SelectedOptionsRecyclerViewAdapter

    private lateinit var mAvailableColorsAndOptionsViewModel: AvailableColorsAndOptionsViewModel

    private var tracker: SelectionTracker<Long>? = null

    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var optionsRecyclerView: RecyclerView

    private lateinit var versionImage: ImageView
    private lateinit var modelTextView: TextView
    private lateinit var versionTextView: TextView
    private lateinit var brandLogo: ImageView
    private lateinit var buyButton: Button

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_composing)

        val actionbar = supportActionBar
        actionbar!!.title = this.intent.getStringExtra("versionName")
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)


        contentView = content_view_composing
        noInternetTextView = no_internet_composing
        progressBar = progress_bar_composing
        versionImage = imageView_composing
        colorsRecyclerView = colors_recycler_view_composing
        optionsRecyclerView = options_recycler_view_composing
        buyButton = command_button
        modelTextView = model_text_composing
        versionTextView = version_text_composing
        brandLogo = brand_logo_composing

        contentView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()

        mAvailableColorsAndOptionsViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory { AvailableColorsAndOptionsViewModel(this.intent.getStringExtra("versionId")) }
        ).get(AvailableColorsAndOptionsViewModel::class.java)

        mAvailableColorsAndOptionsViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mAvailableColorsAndOptionsViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mAvailableColorsAndOptionsViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentView.visibility = it
            }
        })

        mAvailableColorsAndOptionsViewModel.optionsAndColorsLiveData.observe(this, Observer { version ->
            version?.let {

                //TODO versionTextView.text = it.name
                modelTextView.text = this.intent.getStringExtra("modelName")

                Glide.with(brandLogo.context)
                    .load(this.intent.getStringExtra("brandLogo"))
                    .into(brandLogo)

                colorsRecyclerViewAdapter = ColorsRecyclerViewAdapter(it.colors)
                optionsRecyclerViewAdapter = SelectedOptionsRecyclerViewAdapter(it.options)

                colorsRecyclerView.adapter = colorsRecyclerViewAdapter
                optionsRecyclerView.adapter = optionsRecyclerViewAdapter

                //TODO Glide.with(versionImage.context)
                //    .load(it.image)
                //    .into(versionImage)


                tracker = SelectionTracker.Builder(
                    "mySelection",
                    colorsRecyclerView,
                    MyItemKeyProvider(colorsRecyclerView),
                    MyItemDetailsLookup(colorsRecyclerView),
                    StorageStrategy.createLongStorage()
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectSingleAnything()
                ).build()
                colorsRecyclerViewAdapter.tracker = tracker

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

        buyButton.setOnClickListener {
            //TODO implement the Post logic of the command
        }

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
