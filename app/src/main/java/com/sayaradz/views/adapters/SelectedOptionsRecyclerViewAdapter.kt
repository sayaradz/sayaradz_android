package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Option
import kotlinx.android.synthetic.main.selected_option_recycler_view_item.view.*

class SelectedOptionsRecyclerViewAdapter(private val optionsArrayList: List<Option>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_option_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val option = this.optionsArrayList!![position]

            viewHolder.selectedOption.text = option.name

        }

        //TODO Deal with the logic when the checkbox is checked or unchecked
    }

    override fun getItemCount(): Int {
        if (optionsArrayList != null) {
            return optionsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var selectedOption: CheckBox = view.selected_option

    }
}
