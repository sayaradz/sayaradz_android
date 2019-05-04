package com.sayaradz.views.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Color
import com.sayaradz.models.ColorRepository
import com.sayaradz.models.Option
import com.sayaradz.models.OptionRepository
import com.sayaradz.views.MyItemDetailsLookup
import com.sayaradz.views.MyItemKeyProvider
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter
import com.sayaradz.views.adapters.OptionsRecyclerViewAdapter
import com.sayaradz.views.fragments.OrderDialogFragment
import kotlinx.android.synthetic.main.activity_new_cars_details.*

class NewCarsDetailsActivity : AppCompatActivity(), OrderDialogFragment.OrderDialogListener {

    private var optionsList: List<Option>? = null
    private var colorsList: List<Color>? = null
    private lateinit var colorsRecyclerViewAdapter: ColorsRecyclerViewAdapter
    private lateinit var optionsRecyclerViewAdapter: OptionsRecyclerViewAdapter

    var tracker: SelectionTracker<Long>? = null

    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var optionsRecyclerView: RecyclerView

    private lateinit var buyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_cars_details)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()
        colorsList = ColorRepository.modelsList
        optionsList = OptionRepository.modelsList

        colorsRecyclerViewAdapter = ColorsRecyclerViewAdapter(colorsList)
        optionsRecyclerViewAdapter = OptionsRecyclerViewAdapter(optionsList)

        colorsRecyclerView = findViewById(R.id.colors_recycler_view)
        val mLayoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        colorsRecyclerView.layoutManager = mLayoutManager
        colorsRecyclerView.itemAnimator = DefaultItemAnimator()
        colorsRecyclerView.isNestedScrollingEnabled = false

        colorsRecyclerView.adapter = colorsRecyclerViewAdapter



        optionsRecyclerView = findViewById(R.id.options_recycler_view)
        val vLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        optionsRecyclerView.layoutManager = vLayoutManager
        optionsRecyclerView.itemAnimator = DefaultItemAnimator()
        optionsRecyclerView.isNestedScrollingEnabled = false

        optionsRecyclerView.adapter = optionsRecyclerViewAdapter

        //modelsRecyclerViewAdapter.setOnItemClickListener(this)


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



        buyButton = command_button

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
}
