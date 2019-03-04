package com.elbadri.apps.sayaradz.adapter

import android.app.Activity
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.elbadri.apps.sayaradz.R
import com.elbadri.apps.sayaradz.model.Company
import org.w3c.dom.Text

class MyGridAdapter(private var activity: Activity, private var items: ArrayList<Company>) : BaseAdapter() {
    private class ViewHolder(row: View?) {
        var lblName: TextView? = null
        var lblNetworth: TextView? = null
        var imgCompany: ImageView? = null

        init {
            this.lblName = row?.findViewById<TextView>(R.id.lbl_name)
            this.lblNetworth = row?.findViewById<TextView>(R.id.lbl_networth)
            this.imgCompany = row?.findViewById<ImageView>(R.id.img_company)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.single_company, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var company = items[position]
        viewHolder.lblName?.text = company.company_name
        viewHolder.lblNetworth?.text = company.company_networth


        viewHolder.imgCompany?.setImageResource(company.company_photo!!)

        return view as View
    }

    override fun getItem(i: Int): Company {
        return items[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}
