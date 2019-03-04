package com.elbadri.apps.sayaradz.activity

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.elbadri.apps.sayaradz.R
import com.elbadri.apps.sayaradz.activity.Models.Brand
import kotlinx.android.synthetic.main.brands_activity_recycler_view_item.view.*


class BrandsActivityRecyclerViewAdapter (val items : ArrayList<Brand>, val context: Context) : RecyclerView.Adapter<BrandsActivityRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.brands_activity_recycler_view_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mBrandLogo.setImageURI(Uri.parse(items.get(position).logo))
        holder.mBrandName.text = items.get(position).name
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        val mBrandLogo: ImageView = view.brand_pic
        val mBrandName: TextView = view.textView


    }

}
