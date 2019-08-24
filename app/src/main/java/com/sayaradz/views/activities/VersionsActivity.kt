package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.sayaradz.R
import com.sayaradz.models.Model
import com.sayaradz.models.Order
import com.sayaradz.models.Version
import com.sayaradz.viewModels.*
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import com.sayaradz.views.fragments.dialog_fragments.CompareDialogFragment
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment
import kotlinx.android.synthetic.main.activity_models.*
import kotlinx.android.synthetic.main.versions_models_view.*


class VersionsActivity : AppCompatActivity(),
    VersionsRecyclerViewAdapter.OnItemClickListener,
    CompareDialogFragment.OrderDialogListener, OrderDialogFragment.OrderDialogListener {

    private lateinit var versionsRecyclerViewAdapter: VersionsRecyclerViewAdapter
    private lateinit var fAButton: ExtendedFloatingActionButton

    private lateinit var titleTextView: TextView
    private var mModelViewModel: ModelViewModel? = null
    private lateinit var mFollowVersionViewModel: FollowVersionViewModel
    private lateinit var mUnFollowVersionViewModel: UnfollowVersionViewModel
    private lateinit var orderViewModel: CreateOrderViewModel

    // RecyclerView
    private lateinit var versionsRecyclerView: RecyclerView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar


    private lateinit var modelName: String
    private  var brandLogo: String? = null
    private lateinit var orderVersion: Version

    private var versionList: List<Version>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_models)


        val model = this.intent.getSerializableExtra("model") as Model
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = model.name
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        brandLogo = this.intent.getStringExtra("brandLogo")

        versionsRecyclerView = models_recycler_view
        titleTextView = models_text_view
        fAButton = floatingActionButton
        contentNestedScrollView = content_view
        noInternetTextView = no_internet
        progressBar = progress_bar


        fAButton.visibility = View.GONE

        titleTextView.text = getString(R.string.versions_title)
        progressBar.visibility = View.VISIBLE
        fAButton.visibility = View.GONE
        versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(ArrayList())
        versionsRecyclerView.adapter = versionsRecyclerViewAdapter
        modelName = model.name.toString()

        mModelViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory { ModelViewModel(model.id.toString()) }
        ).get(ModelViewModel::class.java)
        mModelViewModel!!.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mModelViewModel!!.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mModelViewModel!!.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mModelViewModel!!.modelLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(it.versions)
                versionsRecyclerView.adapter = versionsRecyclerViewAdapter
                versionsRecyclerViewAdapter.setOnItemClickListener(this)
                versionList = it.versions!!
                fAButton.visibility = View.VISIBLE
            }
        })

        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        versionsRecyclerView.layoutManager = mLayoutManager
        versionsRecyclerView.itemAnimator = DefaultItemAnimator()
        versionsRecyclerView.isNestedScrollingEnabled = false


        fAButton.setIconResource(R.drawable.ic_versus)
        fAButton.setText(R.string.compare_versions)
        fAButton.setOnClickListener {

            val builder = CompareDialogFragment()
            builder.show(supportFragmentManager, "CompareDialogFragment")

        }



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


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        val intent = Intent(view.context, NewCarsDetailsActivity::class.java)
        intent.putExtra("versionId", obj.id)
        intent.putExtra("modelName", modelName)
        intent.putExtra("versionName", obj.name)
        intent.putExtra("brandLogo", brandLogo)
        startActivity(intent)
    }

    override fun onFollowButtonClick(view: View, obj: Version, position: Int) {
        val imageView = view as ImageView

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val id = prefs.getString("id", "")!!

        if (imageView.drawable.constantState == getDrawable(R.drawable.ic_follow)!!.constantState) {

            mFollowVersionViewModel = ViewModelProviders.of(
                this,
                modelsViewModelFactory { FollowVersionViewModel(id, obj.id!!) }
            ).get(FollowVersionViewModel::class.java)

            imageView.setImageResource(R.drawable.ic_followed)

        } else {

            mUnFollowVersionViewModel = ViewModelProviders.of(
                this,
                modelsViewModelFactory { UnfollowVersionViewModel(id, obj.id!!) }
            ).get(UnfollowVersionViewModel::class.java)

            imageView.setImageResource(R.drawable.ic_follow)

        }
    }

    override fun onBuyButtonClick(view: View, obj: Version, position: Int) {
        orderVersion = obj
        val builder = OrderDialogFragment()
        builder.show(supportFragmentManager, "OrderDialogFragment")
    }

    override fun isFollowed(id: String): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = prefs.getString("id", "")!!
        var boolea = false

        var mIsModelFollowedViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory { IsModelFollowedViewModel(userId, id) }
        ).get(IsModelFollowedViewModel::class.java)

        mIsModelFollowedViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                boolea = it.following!!
            }
        })

        return boolea

    }


    override fun onConfirmClick(dialog: DialogFragment, version1: Int, version2: Int) {
        val intent = Intent(this, CompareActivity::class.java)
        intent.putExtra("firstVersion", versionList?.get(version1))
        intent.putExtra("secondVersion", versionList?.get(version2))
        startActivity(intent)
    }

    override fun onFillSpinner(spinner: Spinner) {

        val versionNames = ArrayList<String>()
        for (version: Version in this.versionList!!) {
            version.name?.let { versionNames.add(it) }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, versionNames)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
    }

    override fun onDialogNormalOrderClick(dialog: DialogFragment) {
        //TODO implement the normal order action

        orderViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory {
                CreateOrderViewModel(
                    "",
                    "",
                    Order(orderVersion.id, null, null, null, null, null, null)
                )
            }
        ).get(CreateOrderViewModel::class.java)

        dialog.dismiss()

    }

    override fun onDialogAcceleratedOrderClick(dialog: DialogFragment) {
        //TODO implement the Accelerated order action
        orderViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory {
                CreateOrderViewModel(
                    "",
                    "",
                    Order(orderVersion.id, null, null, null, null, null, null)
                )
            }
        ).get(CreateOrderViewModel::class.java)

        dialog.dismiss()
    }


    private inline fun <VM : ViewModel> modelsViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


}
