package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("_id")
    val id: String? = null,
    val code: String? = null,
    val createdAt: String? = null,
    val versions: List<Any>? = null,
    val value: String? = null,
    val option: String? = null,
    val updatedAt: String? = null,
    val options: List<Option>? = null
)