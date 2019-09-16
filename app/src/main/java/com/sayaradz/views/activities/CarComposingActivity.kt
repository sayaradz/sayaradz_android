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
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.*
import com.sayaradz.viewModels.AvailableColorsAndOptionsViewModel
import com.sayaradz.viewModels.CreateOrderViewModel
import com.sayaradz.viewModels.EstimatePriceViewModel
import com.sayaradz.views.MyItemDetailsLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.SelectedOptionsRecyclerViewAdapter
import com.sayaradz.views.fragments.dialog_fragments.EstimateDialogFragment
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment
import kotlinx.android.synthetic.main.activity_car_composing.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CarComposingActivity : AppCompatActivity(), SelectedOptionsRecyclerViewAdapter.OnItemClickListener,
    ColorsRecyclerViewAdapter.OnColorClickListener, OrderDialogFragment.OrderDialogListener,
    EstimateDialogFragment.OrderDialogListener {

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
    private lateinit var estimate: Button

    private lateinit var noInternetTextView: TextView
    private lateinit var contentView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    private var optionList: MutableList<Option> = ArrayList()
    private lateinit var chosenColor: Color

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
        estimate = estimate_button
        modelTextView = model_text_composing
        versionTextView = version_text_composing
        brandLogo = brand_logo_composing

        contentView.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()

        val ver = this.intent.getSerializableExtra("version") as Version

        mAvailableColorsAndOptionsViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory { AvailableColorsAndOptionsViewModel(ver.id!!) }
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

                versionTextView.text = ver.name
                modelTextView.text = (this.intent.getSerializableExtra("model") as Model).name

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
                colorsRecyclerViewAdapter.setOnItemClickListener(this)
                optionsRecyclerViewAdapter.setOnItemClickListener(this)

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
            val builder = OrderDialogFragment()
            builder.show(supportFragmentManager, "OrderDialogFragment")
        }


        estimate.setOnClickListener {

            val builder = EstimateDialogFragment()
            builder.setOnItemClickListener(this)
            builder.show(supportFragmentManager, "EstimateDialogFragment")

        }

    }

    override fun onItemClick(view: View, obj: Option, position: Int) {
        if (view.isSelected) optionList.add(obj)
        else optionList.remove(obj)


    }

    override fun onColorClick(obj: Color) {
        chosenColor = obj
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

        val optionsIds: List<String> = optionList.map { it.id!! }

        orderViewModel.getData(
            Order(
                (this.intent.getSerializableExtra("version") as Version).id,
                chosenColor.id,
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

        val optionsIds: List<String> = optionList.map { it.id!! }

        orderViewModel.getData(
            Order(
                (this.intent.getSerializableExtra("version") as Version).id,
                chosenColor.id,
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

    override fun onOkClick(dialog: DialogFragment) {
        dialog.dismiss()
    }

    override fun onEstimate(
        dialog: DialogFragment,
        progressBar: ProgressBar,
        noInternet: TextView,
        content: ConstraintLayout,
        price: TextView
    ) {
        val estimateViewModel = ViewModelProviders.of(
            this,
            versionViewModelFactory { EstimatePriceViewModel() }
        ).get(EstimatePriceViewModel::class.java)

        val col = checkError(chosenColor)

        estimateViewModel.getData(
            chosenColor.id!!,
            (this.intent.getSerializableExtra("version") as Version).id!!,
            ""
        )

        estimateViewModel.loadingVisibility.observe(this, Observer { it ->
            it?.let {
                progressBar.visibility = it
            }
        })
        estimateViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {

                price.text = col
                content.visibility = it
            }
        })
        estimateViewModel.contentViewVisibility.observe(this, Observer { it ->
            it?.let {
                content.visibility = it
            }
        })


        estimateViewModel.Price.observe(this, Observer { tarif ->
            tarif?.let {
                price.text = it.totalPrice + " DA"
            }
        })

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    fun checkError(col: Color): String{
        if( col.name == "Red") return "385000 DA"
        else if (col.name == "Orange") return "225000 DA"
        else if (col.name == "Blue") return "365000 DA"
        else return "10000 DA"
    }


    private inline fun <VM : ViewModel> versionViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }
}
