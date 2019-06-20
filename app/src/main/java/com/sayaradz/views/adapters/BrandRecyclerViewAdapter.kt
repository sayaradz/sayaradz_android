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
import com.sayaradz.models.Brand

class BrandRecyclerViewAdapter(private val brandArrayList: List<Brand>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Brand, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.brands_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val brand = this.brandArrayList!![position]

            if(brand!=null ){

                viewHolder.viewName.text = brand.name

                val context = viewHolder.holderCardView.context

                //val id = Utils.getDrawableInt(context, brand.logo)
                //Utils.setImageToImageView(context, viewHolder.itemImageView, id)

                Glide.with(viewHolder.itemImageView.context)
                    .load(brand.logo)
                    .into(viewHolder.itemImageView)

                if (itemClickListener != null) {
                    viewHolder.holderCardView.setOnClickListener { v: View ->
                        itemClickListener!!.onItemClick(
                            v,
                            this.brandArrayList[position], position
                        )
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        if (brandArrayList != null) {
            return brandArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.findViewById(R.id.itemImageView)
        internal var viewName: TextView = view.findViewById(R.id.viewName)
        internal var holderCardView: CardView = view.findViewById(R.id.holderCardView)

    }
}
