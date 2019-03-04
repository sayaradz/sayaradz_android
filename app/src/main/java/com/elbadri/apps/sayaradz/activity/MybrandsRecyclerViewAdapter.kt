package com.elbadri.apps.sayaradz.activity

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.elbadri.apps.sayaradz.R
import com.elbadri.apps.sayaradz.activity.Models.Brand


import com.elbadri.apps.sayaradz.activity.brandsFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_brands.view.*


class MybrandsRecyclerViewAdapter(
    private val mValues: List<Brand>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MybrandsRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Brand
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_brands, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.name
        holder.mBrandLogo.setImageURI(Uri.parse(item.logo))

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mContentView: TextView = mView.brand_name
        val mBrandLogo: ImageView = mView.brand_pic

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
