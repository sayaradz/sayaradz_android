package com.sayaradz.views.adapters

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.models.Color
import kotlinx.android.synthetic.main.colors_recycler_view_item.view.*


class ColorsRecyclerViewAdapter(private val colorsArrayList: List<Color>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var tracker: SelectionTracker<Long>? = null

    private var itemClickListener: OnColorClickListener? = null

    interface OnColorClickListener {
        fun onColorClick(obj: Color)
    }

    fun setOnItemClickListener(mItemClickListener: OnColorClickListener) {
        this.itemClickListener = mItemClickListener
    }


    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.colors_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val color = this.colorsArrayList!![position]

            tracker?.let {
                viewHolder.bind(it.isSelected(position.toLong()))

                if (itemClickListener != null) {
                    Log.d("tagggg", color.code)
                    itemClickListener!!.onColorClick(colorsArrayList[position])

                }
            }


            val colorValue = android.graphics.Color.parseColor(color.value)
            val porterDuffColorFilter = PorterDuffColorFilter(colorValue, PorterDuff.Mode.SRC_ATOP)
            viewHolder.colorOval.colorFilter = porterDuffColorFilter

        }
    }

    override fun getItemCount(): Int {
        if (colorsArrayList != null) {
            return colorsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
                override fun inSelectionHotspot(@NonNull e: MotionEvent): Boolean = true
            }

        fun bind(isActivated: Boolean = false) {
            itemView.isActivated = isActivated
            if (tracker?.isSelected(getItemDetails().selectionKey)!!) {
                this.setIsRecyclable(false)
                check.visibility = View.VISIBLE

                Log.e("gg", "${getItemDetails().selectionKey} selected")
            } else check.visibility = View.GONE
        }

        internal var colorOval: ImageView = view.color_circle
        private var check: ImageView = view.check

    }
}
