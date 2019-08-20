package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Notification
import kotlinx.android.synthetic.main.option_recycler_view_item.view.*

class NotificationsRecyclerViewAdapter(private val notifsArrayList: List<Notification>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val notification = this.notifsArrayList!![position]

            viewHolder.viewName.text = notification.name

        }
    }

    override fun getItemCount(): Int {
        if (notifsArrayList != null) {
            return notifsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var viewName: TextView = view.option
    }

}
