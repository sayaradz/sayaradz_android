package com.sayaradz.models

data class CarCompare(

    val name: String? = null,
    val vOption1: Option? = null,
    var vOption2: Option? = null,
    var colorsV1: List<Color>,
    var colorsV2: List<Color>

)