package com.sayaradz.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Version(
    @SerializedName("_id")
    val id: String? = null,
    @SerializedName("image_url")
    val image: String = "",
    val code: String? = null,
    val createdAt: String? = null,
    val options: List<Option>? = null,
    val colors: List<Color>? = null,
    val name: String? = null,
    val updatedAt: String? = null

): Serializable