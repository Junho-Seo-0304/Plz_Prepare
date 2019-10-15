package com.example.plz_prepare

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class BasketActivity : AppCompatActivity() {

    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val orderList by lazy { intent.extras!!["OrderList"] as ArrayList<Order> }
    private lateinit var database : DatabaseReference
    lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        listView = findViewById(R.id.basket_menu_list)
        listView.adapter = BasketAdapter(this,R.layout.basketed_menu,orderList)

    }
}