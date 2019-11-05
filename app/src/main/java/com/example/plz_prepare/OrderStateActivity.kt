package com.example.plz_prepare

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class OrderStateActivity: AppCompatActivity() {

    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val num by lazy {intent.extras!!["Number"] as Int}
    var routeArrayList = ArrayList<CheckingRoute>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_state)
        routeArrayList.add(CheckingRoute(category,uid,num))
        var CheckingList = findViewById<ListView>(R.id.order_state_menu_list)
        CheckingList.adapter = OrderStateMenuAdapter(this,R.layout.order_state_menu,routeArrayList)
    }
}