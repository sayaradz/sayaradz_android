package com.elbadri.apps.sayaradz.fragment.newCars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.elbadri.apps.sayaradz.R

class NewCarsFragment : Fragment() {

    companion object {
        fun newInstance() = NewCarsFragment()
    }

    private lateinit var viewModel: NewCarsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_cars_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewCarsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
