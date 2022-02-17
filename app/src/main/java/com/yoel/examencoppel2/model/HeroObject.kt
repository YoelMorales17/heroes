package com.yoel.examencoppel2.model

data class HeroObject(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var thumbnail : Thumbnail = Thumbnail(),
    var comics : Int = 0,
    var series : Int = 0
)
