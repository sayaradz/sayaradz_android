package com.sayaradz.views.fragments.newCars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sayaradz.R
import com.sayaradz.viewModels.NewCarsDetailViewModel

class NewCarsDetailFragment : Fragment() {

    companion object {
        fun newInstance() = NewCarsDetailFragment()
    }

    private lateinit var viewModel: NewCarsDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.new_cars_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NewCarsDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
