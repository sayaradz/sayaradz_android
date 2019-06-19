package com.sayaradz.views.activities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
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
import com.sayaradz.viewModels.VersionViewModel
import com.sayaradz.views.MyItemDetailsLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.OptionsRecyclerViewAdapter
import com.sayaradz.views.fragments.OrderDialogFragment
import kotlinx.android.synthetic.main.activity_new_cars_details.*

class NewCarsDetailsActivity : AppCompatActivity(), OrderDialogFragment.OrderDialogListener {

    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private lateinit var optionsRecyclerViewAdapter: OptionsRecyclerViewAdapter

    private lateinit var mVersionViewModel: VersionViewModel

    private var tracker: SelectionTracker<Long>? = null

    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var optionsRecyclerView: RecyclerView

    private lateinit var buyButton: Button
    private lateinit var versionImage: ImageView
    private lateinit var modelTextView: TextView
    private lateinit var versionTextView: TextView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cars_details)


        contentView = content_view_details
        noInternetTextView = no_internet_details
        progressBar = progress_bar_details
        versionImage = imageView
        colorsRecyclerView = colors_recycler_view
        optionsRecyclerView = options_recycler_view
        buyButton = command_button
        modelTextView = model_details_text
        versionTextView = version_details_text

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

                val actionbar = supportActionBar
                actionbar!!.title = it.name
                //set back button
                actionbar.setDisplayHomeAsUpEnabled(true)

                versionTextView.text = it.name
                modelTextView.text = this.intent.getStringExtra("modelName")

                colorsRecyclerViewAdapter = ColorsRecyclerViewAdapter(it.colors)
                optionsRecyclerViewAdapter = OptionsRecyclerViewAdapter(it.options)

                colorsRecyclerView.adapter = colorsRecyclerViewAdapter
                optionsRecyclerView.adapter = optionsRecyclerViewAdapter

                Glide.with(versionImage.context)
                    .load(it.image)
                    .into(versionImage)

                tracker = SelectionTracker.Builder<Long>(
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

        //colorsList = ColorRepository.modelsList
        //optionsList = OptionRepository.modelsList

        val mLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        colorsRecyclerView.layoutManager = mLayoutManager
        colorsRecyclerView.itemAnimator = DefaultItemAnimator()
        colorsRecyclerView.isNestedScrollingEnabled = false


        val vLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        optionsRecyclerView.layoutManager = vLayoutManager
        optionsRecyclerView.itemAnimator = DefaultItemAnimator()
        optionsRecyclerView.isNestedScrollingEnabled = false

        //modelsRecyclerViewAdapter.setOnItemClickListener(this)

        buyButton.setOnClickListener {

            val builder = OrderDialogFragment()
            builder.show(supportFragmentManager, "OrderDialogFragment")


        }
    }

    override fun onDialogNormalOrderClick(dialog: DialogFragment) {
        Toast.makeText(
            this,
            "Clicked button",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDialogAcceleratedOrderClick(dialog: DialogFragment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
