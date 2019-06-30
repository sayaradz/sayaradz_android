package com.sayaradz.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sayaradz.R
import com.sayaradz.models.Model
import kotlinx.android.synthetic.main.models_recycler_view_item.view.*

class ModelsRecyclerViewAdapter(private val modelsArrayList: List<Model>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Model, position: Int)
        fun onFollowButtonClick(view: View, obj: Model, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.models_recycler_view_item, parent, false)

        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            val model = this.modelsArrayList?.get(position)

            if (model != null) {

                viewHolder.detailTextView.text = "Versions"

                viewHolder.buyButton.visibility = View.GONE

                viewHolder.viewName.text = model.name

                val context = viewHolder.holderCardView.context

                //val id = Utils.getDrawableInt(context, model.image)
                //Utils.setImageToImageView(context, viewHolder.itemImageView, id)

                Glide.with(context)
                    .load(model.image)
                    .into(viewHolder.itemImageView)


                if (itemClickListener != null) {
                    viewHolder.holderCardView.setOnClickListener { v: View ->
                        this.modelsArrayList!![position].let {
                            itemClickListener!!.onItemClick(
                                v,
                                it, position
                            )
                        }
                    }

                    viewHolder.followButton.setOnClickListener {
                        itemClickListener!!.onFollowButtonClick(it, this.modelsArrayList!![position], position)
                        viewHolder.followButton.setImageResource(R.drawable.ic_followed)
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        if (modelsArrayList != null) {
            return modelsArrayList.size
        }
        return 0
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.findViewById(R.id.car_pic)
        internal var viewName: TextView = view.findViewById(R.id.model_button)
        internal var holderCardView: CardView = view.findViewById(R.id.card_view_holder)
        internal var detailTextView: TextView = view.findViewById(R.id.version_text_view)
        internal var buyButton: ImageView = view.buy_button
        internal var followButton: ImageView = view.follow_button

    }
}
