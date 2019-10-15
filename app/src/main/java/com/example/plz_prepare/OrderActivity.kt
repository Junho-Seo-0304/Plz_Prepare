package com.example.plz_prepare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        basket_cash.setOnClickListener {
            val nextIntent = Intent(this, BasketActivity::class.java)
            startActivity(nextIntent)
        }

        order_state.setOnClickListener {
            val nextIntent = Intent(this, OrderStateActivity::class.java)
            startActivity(nextIntent)
        }

    }


}