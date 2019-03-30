package com.sayaradz.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Model
import com.sayaradz.models.ModelRepository
import com.sayaradz.models.Version
import com.sayaradz.models.VersionRepository
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter

class ModelsActivity : AppCompatActivity(), ModelsRecyclerViewAdapter.OnItemClickListener,
    VersionsRecyclerViewAdapter.OnItemClickListener {

    private var modelsList: List<Model>? = null
    private var versionsList: List<Version>? = null
    private lateinit var modelsRecyclerViewAdapter: ModelsRecyclerViewAdapter
    private lateinit var versionsRecyclerViewAdapter: VersionsRecyclerViewAdapter

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
    }

    override fun onItemClick(view: View, obj: Model, position: Int) {

        titleTextView.text = "Versions"

        //TODO fix the data source
        versionsList = VersionRepository.modelsList
        versionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(versionsList)

        modelsRecyclerView.adapter = versionsRecyclerViewAdapter
        versionsRecyclerViewAdapter.setOnItemClickListener(this)
    }

    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        startActivity(Intent(this, NewCarsDetailsActivity::class.java))
    }

    override fun onBackPressed() {
        if (titleTextView.text.toString() == "Versions") {
            titleTextView.text = "Modéles"


            //TODO fix the data source
            modelsList = ModelRepository.modelsList
            modelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(modelsList)

            modelsRecyclerView.adapter = modelsRecyclerViewAdapter
            modelsRecyclerViewAdapter.setOnItemClickListener(this)
        } else super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
