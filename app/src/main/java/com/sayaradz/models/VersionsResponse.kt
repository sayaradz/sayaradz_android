package com.sayaradz.models

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable

data class VersionsResponse(
    val count: Int,
    @SerializedName("rows")
    val brands: List<Version>
)