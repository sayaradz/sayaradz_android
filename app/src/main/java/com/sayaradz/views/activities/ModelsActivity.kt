package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.sayaradz.R
import com.sayaradz.models.Model
import com.sayaradz.models.Version
import com.sayaradz.viewModels.BrandViewModel
import com.sayaradz.viewModels.ModelViewModel
import com.sayaradz.views.MyChosenModelLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ModelChooseComposeCarAdapter
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionChooseComposeCarAdapter
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import com.sayaradz.views.fragments.ComposeModelDialogFragment
import com.sayaradz.views.fragments.ComposeVersionDialogFragment
import kotlinx.android.synthetic.main.activity_models.*
import kotlinx.android.synthetic.main.versions_models_view.*


class ModelsActivity : AppCompatActivity(), ModelsRecyclerViewAdapter.OnItemClickListener,
    ComposeModelDialogFragment.ComposeDialogListener, ComposeVersionDialogFragment.ComposeDialogListener,
    ModelChooseComposeCarAdapter.SelectModelListner {

    private lateinit var modelsRecyclerViewAdapter: ModelsRecyclerViewAdapter
    private lateinit var fAButton: ExtendedFloatingActionButton

    private lateinit var titleTextView: TextView
    private lateinit var mBrandViewModel: BrandViewModel

    private lateinit var mModelViewModel1: ModelViewModel

    // RecyclerView
    private lateinit var modelsRecyclerView: RecyclerView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar

    private lateinit var brandLogo: String

    private var versionList: List<Version>? = null
    private lateinit var modelList: List<Model>
    private lateinit var chosenModel: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_models)


        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = this.intent.getStringExtra("brandName")
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        brandLogo = this.intent.getStringExtra("brandLogo")

        modelsRecyclerView = models_recycler_view
        titleTextView = models_text_view
        fAButton = floatingActionButton
        contentNestedScrollView = content_view
        noInternetTextView = no_internet
        progressBar = progress_bar


        fAButton.visibility = View.GONE
        mBrandViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory { BrandViewModel(this.intent.getStringExtra("brandId")) }
        ).get(BrandViewModel::class.java)
        mBrandViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mBrandViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mBrandViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mBrandViewModel.modelLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                modelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(it.models)
                modelsRecyclerView.adapter = modelsRecyclerViewAdapter
                modelsRecyclerViewAdapter.setOnItemClickListener(this)
                fAButton.visibility = View.VISIBLE
                modelList = it.models!!
            }
        })




        contentNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                //scroll down
                fAButton.shrink(true)
            }
            if (scrollY < oldScrollY) {
                //scroll up
                if (!fAButton.isShown) fAButton.visibility = View.VISIBLE
                else fAButton.extend(true)
            }

            if (scrollY == 0) {
                //top scroll
                fAButton.visibility = View.VISIBLE
            }

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // end of the scroll view
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)

                if (v.getChildAt(0).measuredHeight - v.measuredHeight > displayMetrics.heightPixels)
                    fAButton.visibility = View.GONE
            }
        })


        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        modelsRecyclerView.layoutManager = mLayoutManager
        modelsRecyclerView.itemAnimator = DefaultItemAnimator()
        modelsRecyclerView.isNestedScrollingEnabled = false



        fAButton.setOnClickListener {
            //TODO implement the Compose car system
            val builder = ComposeModelDialogFragment()
            builder.show(supportFragmentManager, "ComposeModelDialogFragment")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onItemClick(view: View, obj: Model, position: Int) {
        val intent = Intent(view.context, VersionsActivity::class.java)
        intent.putExtra("model", obj)
        intent.putExtra("brandLogo", brandLogo)
        startActivity(intent)
    }

    override fun onFollowButtonClick(view: View, obj: Model, position: Int) {
        //TODO Implement the follow action for models
    }


    override fun onPopulateVersions(recyclerView: RecyclerView) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        val mLayoutManager =
            LinearLayoutManager(recyclerView.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        val recyclerViewAdapter = VersionChooseComposeCarAdapter(versionList)
        recyclerView.adapter = recyclerViewAdapter
        val tracker = SelectionTracker.Builder(
            "mySelection",
            recyclerView,
            MyItemKeyProvider(recyclerView),
            MyChosenModelLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()
        recyclerViewAdapter.tracker = tracker
    }

    override fun onPopulateModels(recyclerView: RecyclerView) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val mLayoutManager =
            LinearLayoutManager(recyclerView.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false

        val recyclerViewAdapter = ModelChooseComposeCarAdapter(modelList)
        recyclerView.adapter = recyclerViewAdapter
        val tracker = SelectionTracker.Builder(
            "mySelection",
            recyclerView,
            MyItemKeyProvider(recyclerView),
            MyChosenModelLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()
        recyclerViewAdapter.tracker = tracker
        recyclerViewAdapter.setOnItemClickListener(this)
    }

    override fun onSelectModel(modelPosition: Int) {
        chosenModel = modelList[modelPosition]
    }

    override fun onNextClick(dialog: DialogFragment) {

        mModelViewModel1 = ViewModelProviders.of(
            this,
            modelsViewModelFactory { ModelViewModel(chosenModel.id.toString()) }
        ).get(ModelViewModel::class.java)

        mModelViewModel1.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mModelViewModel1.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mModelViewModel1.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mModelViewModel1.modelLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                versionList = it.versions!!
                val builder = ComposeVersionDialogFragment()
                builder.show(supportFragmentManager, "ComposeVersionDialogFragment")

            }
        })

    }

    override fun onConfirmComposeClick(dialog: DialogFragment) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private inline fun <VM : ViewModel> modelsViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


}
