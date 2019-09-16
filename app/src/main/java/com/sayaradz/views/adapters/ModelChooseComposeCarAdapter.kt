package com.sayaradz.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sayaradz.R
import com.sayaradz.models.Model


class ModelChooseComposeCarAdapter(private val modelToChooseArrayList: List<Model>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var tracker: SelectionTracker<Long>? = null

    private lateinit var buttonStateListener: ButtonStateListener
    private lateinit var listener: SelectModelListener

    init {
        setHasStableIds(true)
    }


    interface SelectModelListener {
        fun onSelectModel(modelPosition: Int)
    }

    fun setOnItemClickListener(mItemClickListener: SelectModelListener) {
        this.listener = mItemClickListener
    }

    interface ButtonStateListener {

        fun onButtonStateChanged()
    }

    fun setOnButtonStateListener(mItemStateListener: ButtonStateListener) {
        this.buttonStateListener = mItemStateListener
    }


    override fun getItemId(position: Int): Long = position.toLong()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chosen_model_or_version_item, parent, false)



        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val modelToChoose = modelToChooseArrayList!![position]

            Log.d("tag", modelToChooseArrayList.toString())

            viewHolder.itemNameTextView.text = modelToChoose.name

            val context = viewHolder.constraintLayout.context


            Glide.with(context)
                .load(modelToChoose.image)
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.itemImageView)

            tracker?.let {
                viewHolder.bind(it.isSelected(position.toLong()))
            }


        }
    }

    override fun getItemCount(): Int {

        return modelToChooseArrayList?.size ?: 0
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
                constraintLayout.setBackgroundResource(R.drawable.layout_border)
                listener.onSelectModel(getItemDetails().selectionKey!!.toInt())
                buttonStateListener.onButtonStateChanged()
                Log.e("gg", "${getItemDetails().selectionKey} selected")
            } else {
                Log.e("gg", "${getItemDetails().selectionKey} unselected")
                constraintLayout.setBackgroundResource(0)
                buttonStateListener.onButtonStateChanged()

            }
        }

        internal var itemImageView: ImageView = view.findViewById(R.id.itemImageView)
        internal var itemNameTextView: TextView = view.findViewById(R.id.itemNameTextView)
        internal var constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout_chosen)

    }
}
