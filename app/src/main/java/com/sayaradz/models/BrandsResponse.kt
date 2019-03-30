package com.sayaradz.models

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable

data class BrandsResponse(
    val count: Int,
    @SerializedName("rows")
    val brands: List<Brand>
)