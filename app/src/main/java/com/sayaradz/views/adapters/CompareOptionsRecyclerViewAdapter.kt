package com.sayaradz.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.CarCompare
import com.sayaradz.models.Color
import kotlinx.android.synthetic.main.comparing_recycler_view_item.view.*

class CompareOptionsRecyclerViewAdapter(private val carsArrayList: List<CarCompare>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comparing_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val comparing = this.carsArrayList!![position]

            viewHolder.optionTitle.text = comparing.name
            if (comparing.name == viewHolder.context.getString(R.string.Colors)) {
                if (comparing.colorsV1.isNotEmpty())
                    viewHolder.optionValueV1.text = getColors(comparing.colorsV1)
                else viewHolder.optionValueV1.text = "-"
                if (comparing.colorsV2.isNotEmpty())
                    viewHolder.optionValueV2.text = getColors(comparing.colorsV2)
                else viewHolder.optionValueV2.text = "-"

            } else {
                if (comparing.vOption1 != null)
                    viewHolder.optionValueV1.text = comparing.vOption1.name
                else viewHolder.optionValueV1.text = "-"
                if (comparing.vOption2 != null)
                    viewHolder.optionValueV2.text = comparing.vOption2!!.name
                else viewHolder.optionValueV2.text = "-"
            }

        }
    }

    private fun getColors(colors: List<Color>): String {
        var str = ""
        for (color: Color in colors) {
            if(str!="") str+= ", "
            str += color.name
        }
        return str
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
        internal var context: Context = view.context

    }
}
