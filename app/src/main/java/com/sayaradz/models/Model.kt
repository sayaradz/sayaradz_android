package com.sayaradz.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Model(
    @SerializedName("_id")
    var id: String? = null,
    @SerializedName("image_url")
    var image: String = "",
    var code: String? = null,
    var createdAt: String? = null,
    var  versions: List<Version>? = null,
    var name: String? = null,
    var updatedAt: String? = null
): Serializable