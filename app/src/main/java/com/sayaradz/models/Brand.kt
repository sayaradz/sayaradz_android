package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class Brand(
    @SerializedName("_id")
    val id: String? = null,
    val logo:String = "",
    val code: String? = null,
    val createdAt: String? = null,
    val models: List<Any>? = null,
    val name: String? = null,
    val updatedAt: String? = null
)
