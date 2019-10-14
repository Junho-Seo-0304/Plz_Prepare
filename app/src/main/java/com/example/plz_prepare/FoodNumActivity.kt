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

    val food by lazy {intent.extras!!["Food"] as Food}
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
        var order = Order(food, num = 0)
        var button = findViewById<Button>(R.id.button)
        var num = Integer.parseInt(foodQuantity.text.toString())

        imgView.setImageResource(food.image)
        foodName.text=food.name
        foodPrice.text=food.price.toString()


            button.text = (food.price * num).toString() + "원 장바구니 담기"



        button.setOnClickListener{view ->
                val numIntent = Intent(this,MainActivity::class.java)
                order= Order(food,num)
                numIntent.putExtra("Order",order)
                setResult(1,numIntent)
                finish()
        }

        minusButton.setOnClickListener {
            num--

        }

        plus_button.setOnClickListener {
            num++

        }


    }
}
