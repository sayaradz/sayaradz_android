package com.sayaradz.views.fragments.newCars

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
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
import com.sayaradz.models.Model
import com.sayaradz.viewModels.FollowModelViewModel
import com.sayaradz.viewModels.FollowedModelViewModel
import com.sayaradz.viewModels.IsModelFollowedViewModel
import com.sayaradz.viewModels.UnfollowModelViewModel
import com.sayaradz.views.activities.VersionsActivity
import com.sayaradz.views.adapters.ModelsRecyclerViewAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FollowedModelFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FollowedModelFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FollowedModelFragment : Fragment(), ModelsRecyclerViewAdapter.OnItemClickListener {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mFollowedModelViewModel: FollowedModelViewModel
    private lateinit var mFollowModelViewModel: FollowModelViewModel
    private lateinit var mUnFollowModelViewModel: UnfollowModelViewModel

    // RecyclerView
    private lateinit var notifsRecyclerView: RecyclerView
    private lateinit var followedModelsRecyclerViewAdapter: ModelsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: ConstraintLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_followed_model, container, false)

        notifsRecyclerView = view.findViewById(R.id.followed_model_recycler_view)

        contentNestedScrollView = view.findViewById(R.id.content_view_followed_model)
        noInternetTextView = view.findViewById(R.id.no_internet_followed_model)
        progressBar = view.findViewById(R.id.progress_bar_followed_model)

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)
        val id = prefs.getString("id", "")!!

        mFollowedModelViewModel = ViewModelProviders.of(
            this,
            viewModelFactory { FollowedModelViewModel(id) }
        ).get(FollowedModelViewModel::class.java)

        mFollowedModelViewModel.loadingVisibility.observe(this, Observer { progressBar ->
            progressBar?.let {
                this.progressBar.visibility = it
            }
        })
        mFollowedModelViewModel.internetErrorVisibility.observe(this, Observer { internet ->
            internet?.let {
                noInternetTextView.visibility = it
            }
        })
        mFollowedModelViewModel.contentViewVisibility.observe(this, Observer { content ->
            content?.let {
                contentNestedScrollView.visibility = it
            }
        })

        mFollowedModelViewModel.versionLiveData.observe(this, Observer { Response ->
            Response?.let {
                Log.e("kjhkj", it.toString())
                it.models
                followedModelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(it.models)
                notifsRecyclerView.adapter = followedModelsRecyclerViewAdapter
                followedModelsRecyclerViewAdapter.setOnItemClickListener(this)
            }
        })

        val mLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        notifsRecyclerView.layoutManager = mLayoutManager
        notifsRecyclerView.itemAnimator = DefaultItemAnimator()
        notifsRecyclerView.isNestedScrollingEnabled = false


        return view
    }


    override fun onItemClick(view: View, obj: Model, position: Int) {
        val intent = Intent(view.context, VersionsActivity::class.java)
        intent.putExtra("model", obj)
        startActivity(intent)
    }

    override fun onFollowButtonClick(view: View, obj: Model, position: Int) {
        val imageView = view as ImageView

        val prefs = PreferenceManager.getDefaultSharedPreferences(this.context)
        val id = prefs.getString("id", "")!!

        if (imageView.drawable.constantState == resources.getDrawable(R.drawable.ic_follow)!!.constantState) {

            mFollowModelViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { FollowModelViewModel(id, obj.id!!) }
            ).get(FollowModelViewModel::class.java)

            mFollowModelViewModel.brandLiveData.observe(this, Observer { brandsResponse ->
                brandsResponse?.let {
                    Log.e("kjhkj", it.toString())

                }
            })
            imageView.setImageResource(R.drawable.ic_followed)

        } else {

            mUnFollowModelViewModel = ViewModelProviders.of(
                this,
                viewModelFactory { UnfollowModelViewModel(id, obj.id!!) }
            ).get(UnfollowModelViewModel::class.java)

            imageView.setImageResource(R.drawable.ic_follow)

        }

    }

    override fun isFollowed(id: String): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val userId = prefs.getString("id", "")!!
        var boolea = false

        var mIsModelFollowedViewModel = ViewModelProviders.of(
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

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FollowedModelFragment().apply {

            }
    }
}
