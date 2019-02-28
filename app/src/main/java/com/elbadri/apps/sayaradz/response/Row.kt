package com.elbadri.apps.sayaradz.response

import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val code: String,
    val createdAt: String,
    val models: List<Any>,
    val name: String,
    val updatedAt: String
)