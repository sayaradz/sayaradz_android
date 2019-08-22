package com.sayaradz.models

import java.io.Serializable

data class Notification(
    var id: String? = null,
    var message: String? = null,
    var seen: Boolean? = null,
    var concern_user: String? = null
) : Serializable
