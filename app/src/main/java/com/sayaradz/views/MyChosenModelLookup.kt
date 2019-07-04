package com.sayaradz.views

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.views.adapters.ModelChooseComposeCarAdapter
import com.sayaradz.views.adapters.VersionChooseComposeCarAdapter


internal class MyChosenModelLookup(private val mRecyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = mRecyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val holder = mRecyclerView.getChildViewHolder(view)
            if (holder is ModelChooseComposeCarAdapter.ItemViewHolder) {
                return holder.getItemDetails()
            } else if (holder is VersionChooseComposeCarAdapter.ItemViewHolder) {
                return holder.getItemDetails()
            }
        }
        return null
    }
}