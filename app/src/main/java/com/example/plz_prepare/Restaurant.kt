package com.example.plz_prepare

// Firebase realtime database에 있는 Restaurant 정보를 불러 오기 위해 레스토랑 정보를 가지고 있는 class
data class Restaurant (
    var Rname : String? = null,
    var RlocationX:Double? = 0.00,
    var RlocationY:Double? = 0.00,
    var RMenu : List<Menu>? = null
)