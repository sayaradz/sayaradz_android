package com.sayaradz.models

import java.io.Serializable

data class User(

    var email: String? = null,
    var firebase_id: String? = null,
    var fcm_id: String? = null

) : Serializable