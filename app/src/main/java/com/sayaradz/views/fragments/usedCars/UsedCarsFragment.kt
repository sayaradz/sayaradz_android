package com.sayaradz.views.fragments.usedCars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.sayaradz.R
import com.sayaradz.viewModels.UsedCarsViewModel

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
