package com.sayaradz.models

data class RecievedOrder(
    val version: Version? = null,
    val color: Color? = null,
    val amount: String? = null,
    val order_date: String? = null,
    val options: List<Option>? = null,
    val order_type: String? = null,
    val order_status: String? = null
)

