package com.sayaradz.views.fragments.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Version
import com.sayaradz.models.VersionRepository
import com.sayaradz.views.adapters.HomeNewCarsRecyclerViewAdapter
import com.sayaradz.views.adapters.HomeOldCarsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var newCarsRecyclerView: RecyclerView
    private lateinit var oldCarsRecyclerView: RecyclerView
    private lateinit var newCarsCollectionButton: Button
    private lateinit var oldCarsCollectionButton: Button

    private lateinit var newCarsRecyclerViewAdapter: HomeNewCarsRecyclerViewAdapter
    private lateinit var oldCarsRecyclerViewAdapter: HomeOldCarsRecyclerViewAdapter

    private var newCarsList: List<Version>? = null
    private var oldCarsList: List<Version>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        newCarsList = VersionRepository.modelsList
        oldCarsList = VersionRepository.modelsList

        newCarsRecyclerView = view.findViewById(R.id.new_recycler_view)
        oldCarsRecyclerView = view.findViewById(R.id.old_recycler_view)

        val mLayoutManager = LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        newCarsRecyclerView.layoutManager = mLayoutManager
        newCarsRecyclerView.itemAnimator = DefaultItemAnimator()
        newCarsRecyclerView.isNestedScrollingEnabled = false

        val mLayoutManager1 =
            LinearLayoutManager(view.context.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        oldCarsRecyclerView.layoutManager = mLayoutManager1
        oldCarsRecyclerView.itemAnimator = DefaultItemAnimator()
        oldCarsRecyclerView.isNestedScrollingEnabled = false

        newCarsRecyclerViewAdapter = HomeNewCarsRecyclerViewAdapter(newCarsList)
        oldCarsRecyclerViewAdapter = HomeOldCarsRecyclerViewAdapter(oldCarsList)

        newCarsRecyclerView.adapter = newCarsRecyclerViewAdapter
        oldCarsRecyclerView.adapter = oldCarsRecyclerViewAdapter

        newCarsCollectionButton = view.newCarsCollection
        newCarsCollectionButton.setOnClickListener {
            view.findNavController().navigate(R.id.newCarsMain)
        }

        oldCarsCollectionButton = view.oldCarsCollection
        oldCarsCollectionButton.setOnClickListener {
            view.findNavController().navigate(R.id.usedCarsMain)
        }


        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }
}
