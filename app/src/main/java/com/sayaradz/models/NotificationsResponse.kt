package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class NotificationsResponse(
    val count: Int,
    @SerializedName("rows")
    val notifications: List<Notification>
)