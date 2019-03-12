package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.Utils.Utils
import com.sayaradz.models.Brand


class NewCarsBrandAdapter(private val brandArrayList: List<Brand>?) :
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
            .inflate(R.layout.new_cars_main_fragment_brand_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val newCarsBrand = brandArrayList!![position]

            viewHolder.itemNameTextView.text = newCarsBrand.name

            val context = viewHolder.constraintLayout.context

            val id = Utils.getDrawableInt(context, newCarsBrand.logo)
            Utils.setCircleImageToImageView(context, viewHolder.itemImageView, id, 6, R.color.TitleColor)

            if (itemClickListener != null) {
                viewHolder.constraintLayout.setOnClickListener { v: View ->
                    itemClickListener!!.onItemClick(
                        v,
                        brandArrayList[position], position
                    )
                }
            }

        }
    }

    override fun getItemCount(): Int {

        return brandArrayList?.size ?: 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView
        internal var itemNameTextView: TextView
        internal var constraintLayout: ConstraintLayout

        init {

            itemImageView = view.findViewById(R.id.itemImageView)
            itemNameTextView = view.findViewById(R.id.itemNameTextView)
            constraintLayout = view.findViewById(R.id.constraintLayout)

        }
    }
}
