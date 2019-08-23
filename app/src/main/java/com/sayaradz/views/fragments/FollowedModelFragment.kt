package com.sayaradz.views.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
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
import com.sayaradz.viewModels.FollowedModelViewModel
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
class FollowedModelFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var mFollowedModelViewModel: FollowedModelViewModel

    // RecyclerView
    private lateinit var notifsRecyclerView: RecyclerView
    private lateinit var followedModelsRecyclerViewAdapter: ModelsRecyclerViewAdapter

    private lateinit var noInternetTextView: TextView
    private lateinit var contentNestedScrollView: NestedScrollView
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
                followedModelsRecyclerViewAdapter = ModelsRecyclerViewAdapter(it)
                notifsRecyclerView.adapter = followedModelsRecyclerViewAdapter
            }
        })

        val mLayoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        notifsRecyclerView.layoutManager = mLayoutManager
        notifsRecyclerView.itemAnimator = DefaultItemAnimator()
        notifsRecyclerView.isNestedScrollingEnabled = false


        return view
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowedModelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FollowedModelFragment().apply {

            }
    }
}
