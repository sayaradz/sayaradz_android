package com.sayaradz.views.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Color

class ColorsRecyclerViewAdapter(private val colorsArrayList: List<Color>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Color, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.colors_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val color = this.colorsArrayList!![position]

            if (color != null) {

                val colorValue = android.graphics.Color.parseColor(color.value)
                val porterDuffColorFilter = PorterDuffColorFilter(colorValue, PorterDuff.Mode.SRC_ATOP)
                viewHolder.colorOval.colorFilter = porterDuffColorFilter

                if (itemClickListener != null) {
                    viewHolder.colorOval.setOnClickListener { v: View ->
                        itemClickListener!!.onItemClick(
                            v,
                            this.colorsArrayList[position], position
                        )
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        if (colorsArrayList != null) {
            return colorsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var colorOval: ImageView = view.findViewById(R.id.color_circle)

    }
}
