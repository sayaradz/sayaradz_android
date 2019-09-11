package com.sayaradz.models

data class Order(
    val version: String? = null,
    val color: String? = null,
    val amount: String? = null,
    val order_date: String? = null,
    val order_type: String? = null,
    val options: List<String>? = null,
    val vehicle: String? = null,
    val order_status: String? = null,
    val firebase_id: String? = null
)

