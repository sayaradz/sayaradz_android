package com.sayaradz.views.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.sayaradz.R
import com.sayaradz.models.Order
import com.sayaradz.models.Version
import com.sayaradz.viewModels.CreateOrderViewModel
import com.sayaradz.views.adapters.MyPagerAdapter
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment
import com.sayaradz.views.fragments.newCars.FollowedVersionFragment
import kotlinx.android.synthetic.main.activity_followed_list.*
import java.text.SimpleDateFormat
import java.util.*


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

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = prefs.getString("id", "")!!

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val orderViewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                CreateOrderViewModel()
            }
        ).get(CreateOrderViewModel::class.java)

        orderViewModel.getData(
            Order(
                orderVersion.id,
                null,
                null,
                formatter.format(date),
                "NORMAL",
                null,
                null,
                null,
                userId
            )
        )

        orderViewModel.state.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                if (it) Toast.makeText(this, "Commande Normale effectuer avec succés!", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Commande Normale échouer!", Toast.LENGTH_SHORT).show()
            }
        })

        dialog.dismiss()

    }

    override fun onDialogAcceleratedOrderClick(dialog: DialogFragment) {

        val prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val userId = prefs.getString("id", "")!!

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val orderViewModel = ViewModelProviders.of(
            this,
            viewModelFactory {
                CreateOrderViewModel()
            }
        ).get(CreateOrderViewModel::class.java)

        orderViewModel.getData(
            Order(
                orderVersion.id,
                null,
                null,
                formatter.format(date),
                "ACCELERATED",
                null,
                null,
                null,
                userId
            )
        )

        orderViewModel.state.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                if (it) Toast.makeText(this, "Commande Normale effectuer avec succés!", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "Commande Normale échouer!", Toast.LENGTH_SHORT).show()
            }
        })

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