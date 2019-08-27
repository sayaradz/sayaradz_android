package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    val count: Int,
    @SerializedName("rows")
    val orders: List<RecievedOrder>
)