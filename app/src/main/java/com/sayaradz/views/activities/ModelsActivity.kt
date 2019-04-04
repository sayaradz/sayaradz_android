package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sayaradz.R
import com.sayaradz.models.Model
import com.sayaradz.models.ModelRepository
import com.sayaradz.models.Version
import com.sayaradz.models.VersionRepository
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import com.sayaradz.views.fragments.CompareDialogFragment
import kotlinx.android.synthetic.main.activity_models.*

class ModelsActivity : AppCompatActivity(), ModelsRecyclerViewAdapter.OnItemClickListener,
    VersionsRecyclerViewAdapter.OnItemClickListener,
    CompareDialogFragment.OrderDialogListener {

    private var modelsList: List<Model>? = null
    private var versionsList: List<Version>? = null
    private lateinit var modelsRecyclerViewAdapter: ModelsRecyclerViewAdapter
    private lateinit var versionsRecyclerViewAdapter: VersionsRecyclerViewAdapter
    private lateinit var fAButton: FloatingActionButton

    private lateinit var titleTextView: TextView

    // RecyclerView
    private lateinit var modelsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_models)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "back"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onStart() {
        super.onStart()

        modelsList = ModelRepository.modelsList

        modelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(modelsList)

        modelsRecyclerView = findViewById(R.id.models_recycler_view)
        val mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        modelsRecyclerView.layoutManager = mLayoutManager
        modelsRecyclerView.itemAnimator = DefaultItemAnimator()
        modelsRecyclerView.isNestedScrollingEnabled = false

        titleTextView = findViewById(R.id.models_text_view)


        modelsRecyclerView.adapter = modelsRecyclerViewAdapter

        modelsRecyclerViewAdapter.setOnItemClickListener(this)

        fAButton = floatingActionButton

        fAButton.setOnClickListener {


        }
    }

    override fun onItemClick(view: View, obj: Model, position: Int) {

        titleTextView.text = "Versions"

        //TODO fix the data source
        versionsList = VersionRepository.modelsList
        versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(versionsList)

        modelsRecyclerView.adapter = versionsRecyclerViewAdapter
        versionsRecyclerViewAdapter.setOnItemClickListener(this)

        fAButton.setOnClickListener {

            val builder = CompareDialogFragment()
            builder.show(supportFragmentManager, "CompareDialogFragment")

        }
    }

    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        startActivity(Intent(this, NewCarsDetailsActivity::class.java))
    }

    override fun onConfirmClick(dialog: DialogFragment) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        startActivity(Intent(this,CompareActivity::class.java))
    }

    override fun onFillSpinner(spinner: Spinner) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        if (titleTextView.text.toString() == "Versions") {
            titleTextView.text = "Modéles"


            //TODO fix the data source
            modelsList = ModelRepository.modelsList
            modelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(modelsList)

            modelsRecyclerView.adapter = modelsRecyclerViewAdapter
            modelsRecyclerViewAdapter.setOnItemClickListener(this)
            fAButton.setOnClickListener {


            }
        } else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
