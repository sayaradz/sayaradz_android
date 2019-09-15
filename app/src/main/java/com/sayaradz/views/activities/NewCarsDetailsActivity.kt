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
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.Order
import com.sayaradz.viewModels.CreateOrderViewModel
import com.sayaradz.viewModels.EstimatePriceViewModel
import com.sayaradz.viewModels.VersionViewModel
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.OptionsRecyclerViewAdapter
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment
import kotlinx.android.synthetic.main.activity_new_cars_details.*
import java.text.SimpleDateFormat
import java.util.*

class NewCarsDetailsActivity : AppCompatActivity(), OrderDialogFragment.OrderDialogListener {

    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private lateinit var optionsRecyclerViewAdapter: OptionsRecyclerViewAdapter

    private lateinit var mVersionViewModel: VersionViewModel

    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var optionsRecyclerView: RecyclerView

    private lateinit var versionImage: ImageView
    private lateinit var modelTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var versionTextView: TextView
    private lateinit var brandLogo: ImageView

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    private lateinit var command: Button

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
        command = command_button
        modelTextView = model_details_text
        versionTextView = version_details_text
        brandLogo = brand_logo
        priceTextView = price

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

        val estimateViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory { EstimatePriceViewModel() }
        ).get(EstimatePriceViewModel::class.java)

        estimateViewModel.getData(
            "",
            "",
            this.intent.getStringExtra("versionId")
        )


        estimateViewModel.Price.observe(this, Observer { tarif ->
            tarif?.let {
                priceTextView.text = it.totalPrice + "DA"
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

        command.setOnClickListener {
            val builder = OrderDialogFragment()
            builder.show(supportFragmentManager, "OrderDialogFragment")
        }

    }

    override fun onDialogNormalOrderClick(dialog: DialogFragment) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = prefs.getString("id", "")!!

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val orderViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory {
                CreateOrderViewModel()
            }
        ).get(CreateOrderViewModel::class.java)

        orderViewModel.getData(
            Order(
                this.intent.getStringExtra("versionId"),
                null,
                null,
                formatter.format(date),
                "NORMAL",
                null,
                null,
                null,
                userId
            )
        )

        orderViewModel.state.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                if (it) Toast.makeText(this, "Commande Normale effectuer avec succés!", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Commande Normale échouer!", Toast.LENGTH_SHORT).show()


            }
        })

        dialog.dismiss()

    }

    override fun onDialogAcceleratedOrderClick(dialog: DialogFragment) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = prefs.getString("id", "")!!

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val orderViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory {
                CreateOrderViewModel()
            }
        ).get(CreateOrderViewModel::class.java)

        orderViewModel.getData(
            Order(
                this.intent.getStringExtra("versionId"),
                null,
                null,
                formatter.format(date),
                "ACCELERATED",
                null,
                null,
                null,
                userId
            )
        )

        orderViewModel.state.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                if (it) Toast.makeText(this, "Commande Acceleré effectuer avec succés!", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Commande Acceleré échouer!", Toast.LENGTH_SHORT).show()


            }
        })

        dialog.dismiss()

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
