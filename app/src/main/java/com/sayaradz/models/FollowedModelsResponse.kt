package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class FollowedModelsResponse(
    val count: Int,
    @SerializedName("rows")
    val models: List<Model>
)