package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Offer
import com.sayaradz.Utils.Utils


class NewCarsOfferAdapter(private val offerArrayList: List<Offer>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Offer, position: Int)
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

            viewHolder.viewName.text = wallpaperItem.viewCount

            val context = viewHolder.holderCardView.context

            val id = Utils.getDrawableInt(context, wallpaperItem.imageName)
            Utils.setImageToImageView(context, viewHolder.itemImageView, id)

            if (itemClickListener != null) {
                viewHolder.holderCardView.setOnClickListener { v: View ->
                    itemClickListener!!.onItemClick(
                        v,
                        this!!.offerArrayList!![position], position
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
        internal var itemImageView: ImageView
        internal var viewName: TextView
        internal var holderCardView: CardView

        init {

            itemImageView = view.findViewById(R.id.itemImageView)
            holderCardView = view.findViewById(R.id.holderCardView)
            viewName = view.findViewById(R.id.viewName)

        }
    }
}
