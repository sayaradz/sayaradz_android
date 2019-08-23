package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
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
import com.sayaradz.viewModels.*
import com.sayaradz.views.MyChosenModelLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ModelChooseComposeCarAdapter
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionChooseComposeCarAdapter
import com.sayaradz.views.fragments.dialog_fragments.ComposeModelDialogFragment
import com.sayaradz.views.fragments.dialog_fragments.ComposeVersionDialogFragment
import kotlinx.android.synthetic.main.activity_models.*
import kotlinx.android.synthetic.main.versions_models_view.*


class ModelsActivity : AppCompatActivity(), ModelsRecyclerViewAdapter.OnItemClickListener,
    ComposeModelDialogFragment.ComposeDialogListener, ComposeVersionDialogFragment.ComposeDialogListener,
    ModelChooseComposeCarAdapter.SelectModelListener, VersionChooseComposeCarAdapter.SelectVersionListener {

    private lateinit var fAButton: ExtendedFloatingActionButton
    private lateinit var titleTextView: TextView

    private lateinit var mBrandViewModel: BrandViewModel
    private lateinit var mAvailableModelsViewModel: AvailableModelsViewModel
    private lateinit var mAvailableVersionsViewModel: AvailableVersionsViewModel
    private lateinit var mFollowViewModel: FollowViewModel
    private lateinit var mUnFollowViewModel: UnfollowViewModel

    // RecyclerView
    private lateinit var modelsRecyclerView: RecyclerView
    private lateinit var modelsRecyclerViewAdapter: ModelsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar

    private lateinit var brandLogo: String

    private var versionList: List<Version>? = null
    private lateinit var modelList: List<Model>
    private lateinit var chosenModel: Model
    private lateinit var chosenVersion: Version
    private lateinit var recyclerPopulatedVersionsViewAdapter: VersionChooseComposeCarAdapter
    private lateinit var tracker: SelectionTracker<Long>

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

    }

    override fun onResume() {
        super.onResume()
        mBrandViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { BrandViewModel(this.intent.getStringExtra("brandId")) }
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
                fAButton.visibility = View.GONE
            }
        })


        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        modelsRecyclerView.layoutManager = mLayoutManager
        modelsRecyclerView.itemAnimator = DefaultItemAnimator()
        modelsRecyclerView.isNestedScrollingEnabled = false



        fAButton.setOnClickListener {
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
        val imageView = view as ImageView

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val id = prefs.getString("id", "")!!

        if (imageView.drawable.constantState == getDrawable(R.drawable.ic_follow)!!.constantState) {

            mFollowViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { FollowViewModel(id, obj.id!!) }
            ).get(FollowViewModel::class.java)

            imageView.setImageResource(R.drawable.ic_followed)

        } else {

            mUnFollowViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { UnfollowViewModel(id, obj.id!!) }
            ).get(UnfollowViewModel::class.java)

            imageView.setImageResource(R.drawable.ic_follow)

        }

    }


    override fun onPopulateVersions(recyclerView: RecyclerView) {
        val mLayoutManager =
            LinearLayoutManager(recyclerView.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        recyclerPopulatedVersionsViewAdapter = VersionChooseComposeCarAdapter(versionList)
        recyclerView.adapter = recyclerPopulatedVersionsViewAdapter
        val tracker = SelectionTracker.Builder(
            "mySelection",
            recyclerView,
            MyItemKeyProvider(recyclerView),
            MyChosenModelLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build()
        recyclerPopulatedVersionsViewAdapter.tracker = tracker
        recyclerPopulatedVersionsViewAdapter.setOnItemClickListener(this)

    }

    override fun onPopulateModels(
        recyclerView: RecyclerView,
        dialog: DialogFragment,
        progressBar: ProgressBar,
        noInternet: TextView,
        content: ConstraintLayout
    ) {

        progressBar.visibility = View.VISIBLE
        content.visibility = View.INVISIBLE

        mAvailableModelsViewModel = ViewModelProviders.of(
            dialog,
            viewModelFactory { AvailableModelsViewModel(this.intent.getStringExtra("brandId")) }
        ).get(AvailableModelsViewModel::class.java)


        mAvailableModelsViewModel.loadingVisibility.observe(dialog, Observer { progress ->
            progress?.let {
                progressBar.visibility = it
            }
        })
        mAvailableModelsViewModel.internetErrorVisibility.observe(dialog, Observer { internet ->
            internet?.let {
                noInternet.visibility = it
            }
        })

        mAvailableModelsViewModel.availableModelsLiveData.observe(dialog, Observer { brandsResponse ->
            brandsResponse?.let {
                modelList = it
                val recyclerViewAdapter = ModelChooseComposeCarAdapter(modelList)
                recyclerView.adapter = recyclerViewAdapter
                tracker = SelectionTracker.Builder(
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
                ComposeModelDialogFragment.invoke(recyclerViewAdapter, dialog as ComposeModelDialogFragment)

            }
        })

        val mLayoutManager =
            LinearLayoutManager(recyclerView.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false

    }


    override fun onSelectModel(modelPosition: Int) {
        chosenModel = modelList[modelPosition]
    }

    override fun onSelectVersion(versionPosition: Int) {
        chosenVersion = versionList!![versionPosition]
    }


    override fun onNextClick(
        dialog: DialogFragment,
        progressBar: ProgressBar,
        noInternet: TextView,
        content: ConstraintLayout
    ) {

        mAvailableVersionsViewModel = ViewModelProviders.of(
            dialog,
            viewModelFactory { AvailableVersionsViewModel(chosenModel.id.toString()) }
        ).get(AvailableVersionsViewModel::class.java)

        progressBar.visibility = View.VISIBLE
        content.visibility = View.INVISIBLE

        mAvailableVersionsViewModel.internetErrorVisibility.observe(dialog, Observer { internet ->
            internet?.let {
                noInternet.visibility = it
                if (it == View.VISIBLE)
                    progressBar.visibility = View.INVISIBLE
            }
        })

        mAvailableVersionsViewModel.availableVersionsLiveData.observe(dialog, Observer { brandsResponse ->
            brandsResponse?.let {
                versionList = it

                val builder = ComposeVersionDialogFragment()
                builder.show(supportFragmentManager, "ComposeVersionDialogFragment")

                val timer = object : CountDownTimer(200, 100) {
                    override fun onTick(p0: Long) {

                    }

                    override fun onFinish() {
                        dialog.dismiss()
                    }
                }
                timer.start()


            }
        })


    }

    override fun onConfirmComposeClick(dialog: DialogFragment) {
        val intent = Intent(this, CarComposingActivity::class.java)
        intent.putExtra("model", chosenModel)
        intent.putExtra("version", chosenVersion)
        startActivity(intent)
    }

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


}
