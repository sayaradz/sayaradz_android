package com.sayaradz.views.fragments.myAccount

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sayaradz.R
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var notifTextView: TextView
    private lateinit var notifImageView: ImageView
    private lateinit var announcesTextView: TextView
    private lateinit var announcesImageView: ImageView
    private lateinit var favTextView: TextView
    private lateinit var favImageView: ImageView
    private lateinit var payTextView: TextView
    private lateinit var payImageView: ImageView
    private lateinit var logoutTextView: TextView
    private lateinit var logoutImageView: ImageView

    private lateinit var profilePic: ImageView
    private lateinit var fullName: TextView
    private lateinit var address: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        profilePic = view.profile_pic
        fullName = view.full_name
        address = view.city_country

        notifImageView = view.notificationIcon
        notifTextView = view.notificaton
        announcesImageView = view.announcesIcon
        announcesTextView = view.announces
        favImageView = view.favoriteIcon
        favTextView = view.favorite
        payImageView = view.paimentIcon
        payTextView = view.paiment
        logoutImageView = view.logoutIcon
        logoutTextView = view.logout

        notifImageView.setOnClickListener {
            listener!!.onNotifPressed()
        }

        notifTextView.setOnClickListener {
            listener!!.onNotifPressed()
        }

        logoutTextView.setOnClickListener {
            listener!!.onSignOutPressed()
        }

        logoutImageView.setOnClickListener {
            listener!!.onSignOutPressed()
        }

        announcesTextView.setOnClickListener {
            listener!!.onAnnouncesPressed()
        }

        announcesImageView.setOnClickListener {
            listener!!.onAnnouncesPressed()
        }

        favTextView.setOnClickListener {
            listener!!.onFavoritesPressed()
        }

        favImageView.setOnClickListener {
            listener!!.onFavoritesPressed()
        }

        payTextView.setOnClickListener {
            listener!!.onPaymentPressed()
        }

        payImageView.setOnClickListener {
            listener!!.onPaymentPressed()
        }

        listener!!.onUpdateUserData(fullName, address, profilePic)

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {

        fun onSignOutPressed()
        fun onNotifPressed()
        fun onAnnouncesPressed()
        fun onPaymentPressed()
        fun onFavoritesPressed()

        fun onUpdateUserData(fullName: TextView, address: TextView, profilePic: ImageView)
    }

}
