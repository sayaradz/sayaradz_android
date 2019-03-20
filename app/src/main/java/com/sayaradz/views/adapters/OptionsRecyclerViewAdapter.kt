package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Option

class OptionsRecyclerViewAdapter(private val optionsArrayList: List<Option>?) :
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
            .inflate(R.layout.option_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val option = this.optionsArrayList!![position]

            if (option != null) {

                viewHolder.optionTitle.text = option.option + ": "
                viewHolder.optionValue.text = option.value

            }

        }
    }

    override fun getItemCount(): Int {
        if (optionsArrayList != null) {
            return optionsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var optionTitle: TextView = view.findViewById(R.id.option)
        internal var optionValue: TextView = view.findViewById(R.id.option_value)

    }
}
