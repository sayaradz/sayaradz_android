package com.sayaradz.models

import com.google.gson.annotations.SerializedName

data class NotifsResponse(
    val count: Int,
    @SerializedName("rows")
    val notifs: List<Notification>
)