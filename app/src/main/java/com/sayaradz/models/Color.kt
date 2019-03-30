package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class Color(
    @SerializedName("_id")
    val id: String? = null,
    val code: String? = null,
    val createdAt: String? = null,
    val versions: List<Any>? = null,
    val value: String? = null,
    val updatedAt: String? = null
)