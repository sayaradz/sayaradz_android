package com.elbadri.apps.sayaradz.fragment.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.elbadri.apps.sayaradz.R
import com.elbadri.apps.sayaradz.adapter.FeatureDashboardWallpaperDashboard1CategoryAdapter
import com.elbadri.apps.sayaradz.adapter.FeatureDashboardWallpaperDashboard1ItemAdapter
import com.elbadri.apps.sayaradz.adapter.MyGridAdapter
import com.elbadri.apps.sayaradz.data.ApiService
import com.elbadri.apps.sayaradz.model.Company
import com.elbadri.apps.sayaradz.model.WallpaperCategory
import com.elbadri.apps.sayaradz.model.WallpaperItem
import com.elbadri.apps.sayaradz.response.BrandsResponse
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.nio.channels.NoConnectionPendingException

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)


    }


}
