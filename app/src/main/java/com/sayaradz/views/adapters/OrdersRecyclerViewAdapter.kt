package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.RecievedOrder
import kotlinx.android.synthetic.main.order_recycler_view_item.view.*

class OrdersRecyclerViewAdapter(private val ordersArrayList: List<RecievedOrder>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val order = this.ordersArrayList!![position]

            if (order.color != null)
                viewHolder.carColor.text = "Coleur: ${order.color.name}"
            else viewHolder.carColor.visibility = View.GONE
            viewHolder.carInfo.text = order.version!!.name + " | " + order.order_type
            if (order.options!!.isNotEmpty())
                viewHolder.carOptions.text = "Options: ${order.options.joinToString { it.name.toString() }}"
            else viewHolder.carOptions.visibility = View.GONE
            if (order.order_status != null)
                viewHolder.pendingStatus.text = "Status: ${order.order_status}"
            else viewHolder.pendingStatus.visibility = View.GONE
            if (order.amount != null)
                viewHolder.price.text = "Prix: ${order.amount}"
            else viewHolder.price.visibility = View.GONE


        }
    }

    override fun getItemCount(): Int {
        if (ordersArrayList != null) {
            return ordersArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var carInfo: TextView = view.car_info
        internal var carColor: TextView = view.car_color
        internal var carOptions: TextView = view.car_options
        internal var pendingStatus: TextView = view.order_status
        internal var price: TextView = view.car_price
        internal var date: TextView = view.order_date
    }

}
