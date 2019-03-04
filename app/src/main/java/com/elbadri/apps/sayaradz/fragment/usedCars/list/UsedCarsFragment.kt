package com.elbadri.apps.sayaradz.fragment.usedCars.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elbadri.apps.sayaradz.R

class UsedCarsFragment : Fragment() {

    companion object {
        fun newInstance() = UsedCarsFragment()
    }

    private lateinit var viewModel: UsedCarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.used_cars_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UsedCarsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}