package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class OptionsResponse(
    val count: Int,
    @SerializedName("rows")
    val brands: List<Option>
)