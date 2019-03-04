package com.elbadri.apps.sayaradz.fragment.newCars

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.elbadri.apps.sayaradz.R
import com.elbadri.apps.sayaradz.adapter.MyGridAdapter
import com.elbadri.apps.sayaradz.data.ApiService
import com.elbadri.apps.sayaradz.fragment.home.HomeViewModel
import com.elbadri.apps.sayaradz.model.Company
import com.elbadri.apps.sayaradz.response.BrandsResponse
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.new_cars_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.channels.NoConnectionPendingException

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
        // TODO: Use the ViewMode

        var adapter: MyGridAdapter? = null
        var companyList: ArrayList<Company>

        val apiService = ApiService()
        GlobalScope.launch(Dispatchers.Main) {

            try {
                val currentWeatherResponse = apiService.getBrands(20).await()
                companyList = generateCompanyData(currentWeatherResponse)
                adapter = MyGridAdapter(requireActivity(), companyList)

                grid.adapter = adapter

                grid.setOnItemClickListener { adapterView, view, i, l ->
                    Toast.makeText(
                        requireContext(),
                        " Selected Company is = " + companyList.get(i).company_name,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: NoConnectionPendingException) {

            }
        }

    }

    private fun generateCompanyData(brands: BrandsResponse): ArrayList<Company> {
        var result = ArrayList<Company>()

        for (item in brands.rows) {
            var company: Company = Company()
            company.company_id = item.id
            company.company_name = item.name
            company.company_networth = item.code
            company.company_photo = R.drawable.kia
            result.add(company)

        }

        return result
    }


}
