package com.example.plz_prepare

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class OrderStateActivity: AppCompatActivity() {


    var changeRoute = arrayListOf<CheckingRoute>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_state)
        if(intent!=null){
            val routeArrayList by lazy { intent.extras!!["Route"] as ArrayList<CheckingRoute>}
            changeRoute = routeArrayList
        }
        var CheckingList = findViewById<ListView>(R.id.order_state_menu_list)
        CheckingList.adapter = OrderStateMenuAdapter(this,R.layout.order_state_menu,changeRoute)
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("NewRoute",changeRoute)
        setResult(2,intent)
        super.onBackPressed()
    }
}