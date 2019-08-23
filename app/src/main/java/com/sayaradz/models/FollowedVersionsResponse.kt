package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class FollowedVersionsResponse(
    val count: Int,
    @SerializedName("rows")
    val versions: List<Version>
)