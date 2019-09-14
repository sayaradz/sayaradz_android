package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.Version


class NewCarsOfferAdapter(private val offerArrayList: List<Version>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onOfferItemClick(view: View, obj: Version, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.new_cars_main_fragment_offer_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val wallpaperItem = this.offerArrayList!![position]

            viewHolder.viewName.text = wallpaperItem.name

            val context = viewHolder.holderCardView.context

            Glide.with(context)
                .load(wallpaperItem.image)
                .into(viewHolder.itemImageView)


            if (itemClickListener != null) {
                viewHolder.holderCardView.setOnClickListener { v: View ->
                    itemClickListener!!.onOfferItemClick(
                        v,
                        this.offerArrayList[position], position
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (offerArrayList != null) {
            return offerArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.findViewById(R.id.itemImageView)
        internal var viewName: TextView = view.findViewById(R.id.viewName)
        internal var holderCardView: CardView = view.findViewById(R.id.holderCardView)

    }
}
