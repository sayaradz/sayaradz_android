package com.elbadri.apps.sayaradz.data.db

import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("_id")
    val id: String? = null,
    val code: String? = null,
    val createdAt: String? = null,
    val models: List<Any>? = null,
    val name: String? = null,
    val updatedAt: String? = null
)