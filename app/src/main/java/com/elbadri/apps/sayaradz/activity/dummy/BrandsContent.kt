package com.elbadri.apps.sayaradz.activity.dummy

import com.elbadri.apps.sayaradz.activity.Models.Brand
import java.util.ArrayList
import java.util.HashMap


object BrandsContent {

    val ITEMS: MutableList<Brand> = ArrayList()

    val ITEM_MAP: MutableMap<String, Brand> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createBrandItem(i))
        }
    }

    private fun addItem(item: Brand) {
        ITEMS.add(item)
        ITEM_MAP.put(item.code, item)
    }

    private fun createBrandItem(position: Int): Brand {
        return Brand(position.toString(), "Item " + position, "www.car-logos.org/wp-content/uploads/2011/09/volkswagen.png")
    }

}
