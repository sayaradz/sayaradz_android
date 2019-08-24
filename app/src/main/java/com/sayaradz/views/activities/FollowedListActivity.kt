package com.sayaradz.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sayaradz.R
import com.sayaradz.models.Order
import com.sayaradz.models.Version
import com.sayaradz.viewModels.CreateOrderViewModel
import com.sayaradz.views.adapters.MyPagerAdapter
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment
import com.sayaradz.views.fragments.newCars.FollowedVersionFragment
import kotlinx.android.synthetic.main.activity_followed_list.*


class FollowedListActivity : AppCompatActivity(), OrderDialogFragment.OrderDialogListener,
    FollowedVersionFragment.OnFragmentInteractionListener {

    private lateinit var orderVersion: Version

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_followed_list)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Mes Favoris"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)

        val fragmentAdapter = MyPagerAdapter(supportFragmentManager, this)
        view_pager.adapter = fragmentAdapter

        tab_layout.setupWithViewPager(view_pager)


    }


    override fun onDialogNormalOrderClick(dialog: DialogFragment) {
        //TODO implement the normal order action

        var orderViewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                CreateOrderViewModel(
                    "",
                    "",
                    Order(orderVersion.id, null, null, null, null, null, null)
                )
            }
        ).get(CreateOrderViewModel::class.java)

        dialog.dismiss()

    }

    override fun onDialogAcceleratedOrderClick(dialog: DialogFragment) {
        //TODO implement the Accelerated order action
        var orderViewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                CreateOrderViewModel(
                    "",
                    "",
                    Order(orderVersion.id, null, null, null, null, null, null)
                )
            }
        ).get(CreateOrderViewModel::class.java)

        dialog.dismiss()
    }

    override fun onVersionSpecification(version: Version) {
        orderVersion = version
    }

    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}