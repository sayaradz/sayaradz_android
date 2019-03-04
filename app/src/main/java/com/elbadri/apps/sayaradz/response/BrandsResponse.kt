package com.elbadri.apps.sayaradz.response

import com.elbadri.apps.sayaradz.data.db.Row

data class BrandsResponse(
    val count: Int,
    val rows: List<Row>
)