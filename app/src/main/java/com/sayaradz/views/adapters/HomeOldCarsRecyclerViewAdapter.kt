package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.Version
import com.sayaradz.utils.Utils

class HomeOldCarsRecyclerViewAdapter(private val versionsArrayList: List<Version>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onVersionItemClick(view: View, obj: Version, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val version = this.versionsArrayList!![position]


            val context = viewHolder.holderCardView.context

            val id = Utils.getDrawableInt(context, version.image)
            Utils.setImageToImageView(context, viewHolder.itemImageView, id)

            if (itemClickListener != null) {
                viewHolder.holderCardView.setOnClickListener { v: View ->
                    itemClickListener!!.onVersionItemClick(
                        v,
                        this.versionsArrayList[position], position
                    )
                }
            }

        }
    }

    override fun getItemCount(): Int {
        if (versionsArrayList != null) {
            return versionsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.findViewById(R.id.cars_pic)
        internal var holderCardView: CardView = view.findViewById(R.id.card_view_holder)

    }
}
