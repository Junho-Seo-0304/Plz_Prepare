package com.example.plz_prepare

data class Restaurant (
    var Rname : String? = null,
    var RlocationX:Double? = 0.00,
    var RlocationY:Double? = 0.00,
    var RMenu : List<Food>? = null
)