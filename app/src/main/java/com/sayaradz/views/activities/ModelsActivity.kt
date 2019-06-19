package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sayaradz.R
import com.sayaradz.models.Model
import com.sayaradz.models.Version
import com.sayaradz.viewModels.BrandViewModel
import com.sayaradz.viewModels.ModelViewModel
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import com.sayaradz.views.fragments.CompareDialogFragment
import kotlinx.android.synthetic.main.activity_models.*
import kotlinx.android.synthetic.main.versions_models_view.*

class ModelsActivity : AppCompatActivity(), ModelsRecyclerViewAdapter.OnItemClickListener,
    VersionsRecyclerViewAdapter.OnItemClickListener,
    CompareDialogFragment.OrderDialogListener {

    private lateinit var modelsRecyclerViewAdapter: ModelsRecyclerViewAdapter
    private lateinit var versionsRecyclerViewAdapter: VersionsRecyclerViewAdapter
    private lateinit var fAButton: FloatingActionButton

    private lateinit var titleTextView: TextView
    private lateinit var mBrandViewModel: BrandViewModel
    private lateinit var mModelViewModel: ModelViewModel

    // RecyclerView
    private lateinit var modelsRecyclerView: RecyclerView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
    private lateinit var progressBar: ProgressBar


    private lateinit var modelName: String
    private lateinit var brandLogo: String

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
            }
        })


        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        modelsRecyclerView.layoutManager = mLayoutManager
        modelsRecyclerView.itemAnimator = DefaultItemAnimator()
        modelsRecyclerView.isNestedScrollingEnabled = false



        fAButton.setOnClickListener {
            //TODO implement the Compose car system
        }

    }


    override fun onItemClick(view: View, obj: Model, position: Int) {

        titleTextView.text = "Versions"
        supportActionBar?.title = obj.name
        progressBar.visibility = View.VISIBLE
        versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(ArrayList())
        modelsRecyclerView.adapter = versionsRecyclerViewAdapter
        modelName = obj.name.toString()

        mModelViewModel = ViewModelProviders.of(
            this,
            modelsViewModelFactory { ModelViewModel(obj.id.toString()) }
        ).get(ModelViewModel::class.java)
        mModelViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mModelViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mModelViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mModelViewModel.modelLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(it.versions)
                modelsRecyclerView.adapter = versionsRecyclerViewAdapter
                versionsRecyclerViewAdapter.setOnItemClickListener(this)
            }
        })

        fAButton.setOnClickListener {

            val builder = CompareDialogFragment()
            builder.show(supportFragmentManager, "CompareDialogFragment")

        }
    }

    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        val intent = Intent(view.context, NewCarsDetailsActivity::class.java)
        intent.putExtra("versionId",obj.id)
        intent.putExtra("modelName",modelName)
        intent.putExtra("versionName", obj.name)
        intent.putExtra("brandLogo", brandLogo)
        startActivity(intent)
    }

    override fun onConfirmClick(dialog: DialogFragment) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        startActivity(Intent(this, CompareActivity::class.java))
    }

    override fun onFillSpinner(spinner: Spinner) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (titleTextView.text.toString() == "Versions") {
            titleTextView.text = "Modéles"
            supportActionBar?.title = this.intent.getStringExtra("brandName")

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
                }
            })

            fAButton.setOnClickListener {
                //TODO reset the fab functionning

            }
        } else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private inline fun <VM : ViewModel> modelsViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


}
