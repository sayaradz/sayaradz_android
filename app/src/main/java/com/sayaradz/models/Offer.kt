package com.sayaradz.models

import com.google.gson.annotations.SerializedName


class Offer(
    @field:SerializedName("name")
    var name: String, @field:SerializedName("image")
    var imageName: String, @field:SerializedName("likeCount")
    var likeCount: String, @field:SerializedName("viewCount")
    var viewCount: String, @field:SerializedName("user")
    var user: User
) {

    inner class User {
        @SerializedName("user_name")
        var nameName: String? = null

        @SerializedName("ago")
        var ago: String? = null

        @SerializedName("user_image")
        var user_image: String? = null
    }
}
