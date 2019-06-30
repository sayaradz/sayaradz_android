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
import kotlinx.android.synthetic.main.models_recycler_view_item.view.*

class VersionsRecyclerViewAdapter(private val versionsArrayList: List<Version>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onVersionItemClick(view: View, obj: Version, position: Int)
        fun onFollowButtonClick(view: View, obj: Version, position: Int)
        fun onBuyButtonClick(view: View, obj: Version, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.models_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val version = this.versionsArrayList!![position]

            viewHolder.detailTextView.text = "Details"

            viewHolder.viewName.text = version.name

            val context = viewHolder.holderCardView.context

            //val id = Utils.getDrawableInt(context, version.image)
            //Utils.setImageToImageView(context, viewHolder.itemImageView, id)

            Glide.with(context)
                .load(version.image)
                .into(viewHolder.itemImageView)

            if (itemClickListener != null) {
                viewHolder.holderCardView.setOnClickListener { v: View ->
                    itemClickListener!!.onVersionItemClick(
                        v,
                        this.versionsArrayList[position], position
                    )
                }

                viewHolder.buyButton.setOnClickListener {
                    itemClickListener!!.onBuyButtonClick(it, this.versionsArrayList[position], position)
                }

                viewHolder.followButton.setOnClickListener {
                    itemClickListener!!.onFollowButtonClick(it, this.versionsArrayList[position], position)
                    viewHolder.followButton.setImageResource(R.drawable.ic_followed)
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
        internal var itemImageView: ImageView = view.findViewById(R.id.car_pic)
        internal var viewName: TextView = view.findViewById(R.id.model_button)
        internal var holderCardView: CardView = view.findViewById(R.id.card_view_holder)
        internal var detailTextView: TextView = view.findViewById(R.id.version_text_view)
        internal var buyButton: ImageView = view.buy_button
        internal var followButton: ImageView = view.follow_button
    }
}
