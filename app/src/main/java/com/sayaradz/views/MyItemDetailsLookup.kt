package com.sayaradz.views

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.views.adapters.ColorsRecyclerViewAdapter


internal class MyItemDetailsLookup(private val mRecyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = mRecyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val holder = mRecyclerView.getChildViewHolder(view)
            if (holder is ColorsRecyclerViewAdapter.ItemViewHolder) {
                return holder.getItemDetails()
            }
        }
        return null
    }
}