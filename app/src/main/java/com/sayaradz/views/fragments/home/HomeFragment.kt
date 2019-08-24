package com.sayaradz.views.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Version
import com.sayaradz.models.VersionRepository
import com.sayaradz.viewModels.TrendingVersionsViewModel
import com.sayaradz.views.activities.NewCarsDetailsActivity
import com.sayaradz.views.adapters.HomeNewCarsRecyclerViewAdapter
import com.sayaradz.views.adapters.HomeOldCarsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), HomeNewCarsRecyclerViewAdapter.OnItemClickListener {


    private lateinit var newCarsRecyclerView: RecyclerView
    private lateinit var oldCarsRecyclerView: RecyclerView
    private lateinit var newCarsCollectionButton: Button
    private lateinit var oldCarsCollectionButton: Button

    private lateinit var newCarsRecyclerViewAdapter: HomeNewCarsRecyclerViewAdapter
    private lateinit var oldCarsRecyclerViewAdapter: HomeOldCarsRecyclerViewAdapter

    private var newCarsList: List<Version>? = null
    private var oldCarsList: List<Version>? = null

    private lateinit var trendingVersionsViewModel: TrendingVersionsViewModel

    private lateinit var noInternetTextView: TextView
    private lateinit var contentLayout: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        oldCarsList = VersionRepository.modelsList

        newCarsRecyclerView = view.findViewById(R.id.new_recycler_view)
        oldCarsRecyclerView = view.findViewById(R.id.old_recycler_view)

        contentLayout = view.findViewById(R.id.holder_home)
        noInternetTextView = view.findViewById(R.id.no_internet_home)
        progressBar = view.findViewById(R.id.progressBar_home)

        trendingVersionsViewModel = ViewModelProviders.of(
            this,
            TrendingVersionsViewModelFactory { TrendingVersionsViewModel() }
        ).get(TrendingVersionsViewModel::class.java)
        trendingVersionsViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        trendingVersionsViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        trendingVersionsViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentLayout.visibility = it
            }
        })

        trendingVersionsViewModel.versionLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                newCarsList = it
                newCarsRecyclerViewAdapter = HomeNewCarsRecyclerViewAdapter(newCarsList)
                newCarsRecyclerView.adapter = newCarsRecyclerViewAdapter
                newCarsCollectionButton = view.newCarsCollection
                newCarsCollectionButton.setOnClickListener {
                    view.findNavController().navigate(R.id.newCarsMain)
                }

            }
        })
        val mLayoutManager = LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        newCarsRecyclerView.layoutManager = mLayoutManager
        newCarsRecyclerView.itemAnimator = DefaultItemAnimator()
        newCarsRecyclerView.isNestedScrollingEnabled = false

        val mLayoutManager1 =
            LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        oldCarsRecyclerView.layoutManager = mLayoutManager1
        oldCarsRecyclerView.itemAnimator = DefaultItemAnimator()
        oldCarsRecyclerView.isNestedScrollingEnabled = false

        oldCarsRecyclerViewAdapter = HomeOldCarsRecyclerViewAdapter(oldCarsList)

        oldCarsRecyclerView.adapter = oldCarsRecyclerViewAdapter

        oldCarsCollectionButton = view.oldCarsCollection
        oldCarsCollectionButton.setOnClickListener {
            view.findNavController().navigate(R.id.usedCarsMain)
        }


        return view
    }

    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        val intent = Intent(view.context, NewCarsDetailsActivity::class.java)
        intent.putExtra("versionId", obj.id)
        intent.putExtra("versionName", obj.name)
        startActivity(intent)

    }


    private inline fun <VM : ViewModel> TrendingVersionsViewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }

}
