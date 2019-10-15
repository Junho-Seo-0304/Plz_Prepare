package com.example.plz_prepare

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_food_num.*

class FoodNumActivity : AppCompatActivity() {

    val food by lazy {intent.extras!!["Food"] as Menu}
    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    var num : Int = 0
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food_num)
        var imgView = findViewById<ImageView>(R.id.imgFood)
        var foodName = findViewById<TextView>(R.id.foodName)
        var foodPrice = findViewById<TextView>(R.id.foodPrice)
        var foodExplain = findViewById<TextView>(R.id.foodExplain)
        var foodQuantity = findViewById<TextView>(R.id.num)
        var minusButton = findViewById<ImageView>(R.id.minus_button)
        var plusButton = findViewById<ImageView>(R.id.plus_button)
        var button = findViewById<Button>(R.id.button)
        var cashBtn = findViewById<Button>(R.id.cash)

        foodName.text=food.Fname
        foodPrice.text=food.Fprice.toString()
        foodExplain.text=food.Fexplain
        foodQuantity.text=num.toString()

        button.setOnClickListener{
            val numIntent = Intent(this,Restaurant::class.java)
            var order = Order(food,num)
            numIntent.putExtra("Order",order)
            setResult(1,numIntent)
            finish()
        }

        cashBtn.setOnClickListener {
            val intent = Intent(this,BasketActivity::class.java)
            var orderList = arrayListOf<Order>()
            orderList.add(Order(food,num))
            intent.putExtra("Category",category)
            intent.putExtra("uid",uid)
            intent.putExtra("OrderList",orderList)
            startActivity(intent)
            finish()
        }

        minusButton.setOnClickListener {
            if(num!=0){
                num--
            }
            foodQuantity.text=num.toString()
        }

        plusButton.setOnClickListener {
            num++
            foodQuantity.text=num.toString()
        }


    }
}
