package com.example.plz_prepare

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_num)
        var imgView = findViewById<ImageView>(R.id.imgFood)
        var foodName = findViewById<TextView>(R.id.foodName)
        var foodPrice = findViewById<TextView>(R.id.foodPrice)
        var foodExplain = findViewById<TextView>(R.id.foodExplain)
        var numberPicker = findViewById<NumberPicker>(R.id.num)
        var num = numberPicker.value
        var order = Order(food,num)
        var button = findViewById<Button>(R.id.button)

        imgView.setImageResource(food.image)
        foodName.text=food.name
        foodPrice.text=food.price.toString()
        foodExplain.text=food.explain

        numberPicker.minValue=0
        numberPicker.maxValue=20
        numberPicker.wrapSelectorWheel = false
        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            num = numberPicker.value
            button.text = (food.price * num).toString() + "원 장바구니 담기"
        }
        button.setOnClickListener{view ->
            val numIntent = Intent(this,MainActivity::class.java)
            order= Order(food,num)
            numIntent.putExtra("Order",order)
            setResult(1,numIntent)
            finish()
        }
    }
}
