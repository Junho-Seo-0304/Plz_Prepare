package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_food_num.*
import java.util.*
import kotlin.collections.ArrayList

class RestaurantActivity : AppCompatActivity() {

    var orderList = ArrayList<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        var Menu_Grid=findViewById<GridView>(R.id.category_gridview)
        var Name_Text=findViewById<TextView>(R.id.menu_name)
        var foodsList = ArrayList<Food>()
        var button=findViewById<Button>(R.id.button)
        var priceState = 0


        val adapter = MenuAdapter(this, foodsList)
        Menu_Grid.adapter = adapter
        Name_Text.text="스타버억"

        Menu_Grid.setOnItemClickListener { parent, view, position, id ->
            val foodIntent = Intent(this,FoodNumActivity::class.java)
            foodIntent.putExtra("Food",foodsList[position])
            startActivityForResult(foodIntent,1)
        }
        button.text=priceState.toString()+"원 결제하기"
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val order = data.extras!!.get("Order") as Order
            orderList.add(order)
            var priceState = 0
            for(i in orderList){
                priceState += i.food!!.fprice*i.num
            }
            button.text=priceState.toString()+"원 결제하기"
        }
    }
}
