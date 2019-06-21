package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Car
import com.sayaradz.models.Option
import kotlinx.android.synthetic.main.comparing_recycler_view_item.view.*

class CompareOptionsRecyclerViewAdapter(private val carsArrayList: List<Car>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Option, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comparing_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val option1 = this.carsArrayList!![0].options!![position]
            val option2 = this.carsArrayList[1].options!![position]

            viewHolder.optionTitle.text = option1.name
            viewHolder.optionValueV1.text = option1.code
            viewHolder.optionValueV2.text = option2.code

        }
    }

    override fun getItemCount(): Int {
        if (carsArrayList != null) {
            return carsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var optionTitle: TextView = view.titleComparingItem
        internal var optionValueV1: TextView = view.contentV1
        internal var optionValueV2: TextView = view.contentV2

    }
}
