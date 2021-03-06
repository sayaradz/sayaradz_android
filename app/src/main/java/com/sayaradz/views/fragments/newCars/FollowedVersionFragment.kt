package com.sayaradz.views.fragments.newCars

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Version
import com.sayaradz.viewModels.FollowVersionViewModel
import com.sayaradz.viewModels.FollowedVersionViewModel
import com.sayaradz.viewModels.IsModelFollowedViewModel
import com.sayaradz.viewModels.UnfollowVersionViewModel
import com.sayaradz.views.activities.NewCarsDetailsActivity
import com.sayaradz.views.adapters.VersionsRecyclerViewAdapter
import com.sayaradz.views.fragments.dialog_fragments.OrderDialogFragment


class FollowedVersionFragment : Fragment(), VersionsRecyclerViewAdapter.OnItemClickListener {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mFollowedVersionViewModel: FollowedVersionViewModel
    private lateinit var mFollowVersionViewModel: FollowVersionViewModel
    private lateinit var mUnFollowVersionViewModel: UnfollowVersionViewModel

    // RecyclerView
    private lateinit var notifsRecyclerView: RecyclerView
    private lateinit var followedVersionsRecyclerViewAdapter: VersionsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_followed_version, container, false)

        notifsRecyclerView = view.findViewById(R.id.followed_version_recycler_view)

        contentNestedScrollView = view.findViewById(R.id.content_view_followed_version)
        noInternetTextView = view.findViewById(R.id.no_internet_followed_version)
        progressBar = view.findViewById(R.id.progress_bar_followed_version)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)
        val id = prefs.getString("id", "")!!

        mFollowedVersionViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { FollowedVersionViewModel(id) }
        ).get(FollowedVersionViewModel::class.java)

        mFollowedVersionViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mFollowedVersionViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mFollowedVersionViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mFollowedVersionViewModel.versionLiveData.observe(this, Observer { Response ->
            Response?.let {

                followedVersionsRecyclerViewAdapter = VersionsRecyclerViewAdapter(it.versions)
                notifsRecyclerView.adapter = followedVersionsRecyclerViewAdapter
                followedVersionsRecyclerViewAdapter.setOnItemClickListener(this)
            }
        })

        val mLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        notifsRecyclerView.layoutManager = mLayoutManager
        notifsRecyclerView.itemAnimator = DefaultItemAnimator()
        notifsRecyclerView.isNestedScrollingEnabled = false


        return view
    }


    override fun onVersionItemClick(view: View, obj: Version, position: Int) {
        val intent = Intent(view.context, NewCarsDetailsActivity::class.java)
        intent.putExtra("versionId", obj.id)
        intent.putExtra("versionName", obj.name)

        startActivity(intent)
    }

    override fun onFollowButtonClick(view: View, obj: Version, position: Int) {
        val imageView = view as ImageView

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)
        val id = prefs.getString("id", "")!!

        if (imageView.drawable.constantState == resources.getDrawable(R.drawable.ic_follow)!!.constantState) {

            mFollowVersionViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { FollowVersionViewModel() }
            ).get(FollowVersionViewModel::class.java)

            mFollowVersionViewModel.getData(id, obj.id!!)

            mFollowVersionViewModel.fol.observe(this, Observer { brandsResponse ->
                brandsResponse?.let {

                    imageView.setImageResource(R.drawable.ic_followed)

                    if (it) Toast.makeText(
                        this.context,
                        "Follow effectuer avec succés!",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        Toast.makeText(this.context, "Follow echoué!! ", Toast.LENGTH_SHORT).show()
                        imageView.setImageResource(R.drawable.ic_follow)
                    }

                }
            })

        } else {

            mUnFollowVersionViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { UnfollowVersionViewModel() }
            ).get(UnfollowVersionViewModel::class.java)

            mUnFollowVersionViewModel.getData(id, obj.id!!)

            Toast.makeText(
                this.context,
                "UnFollow attribuer avec succés!",
                Toast.LENGTH_SHORT
            ).show()
            imageView.setImageResource(R.drawable.ic_follow)

        }
    }

    override fun onBuyButtonClick(view: View, obj: Version, position: Int) {
        listener!!.onVersionSpecification(obj)
        val builder = OrderDialogFragment()
        fragmentManager?.let { builder.show(it, "OrderDialogFragment") }
    }

    override fun isFollowed(id: String): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val userId = prefs.getString("id", "")!!
        var boolea = false

        val mIsModelFollowedViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { IsModelFollowedViewModel(userId, id) }
        ).get(IsModelFollowedViewModel::class.java)

        mIsModelFollowedViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
            brandsResponse?.let {
                boolea = it.following!!
            }
        })

        return boolea

    }


    private inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>): T = f() as T
        }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

        fun onVersionSpecification(version: Version)
    }

}
