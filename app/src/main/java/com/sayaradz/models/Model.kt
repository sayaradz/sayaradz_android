package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class Model(
    @SerializedName("_id")
    val id: String? = null,
    val image: String = "",
    val code: String? = null,
    val createdAt: String? = null,
    val versions: List<Any>? = null,
    val name: String? = null,
    val updatedAt: String? = null
)