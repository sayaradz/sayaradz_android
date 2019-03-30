package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class ModelsResponse(
    val count: Int,
    @SerializedName("rows")
    val brands: List<Model>
)