package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class BrandsResponse(
    val count: Int,
    @SerializedName("rows")
    val brands: List<Brand>
)