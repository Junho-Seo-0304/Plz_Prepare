package com.example.plz_prepare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Menu_Grid=findViewById<GridView>(R.id.menu_Grid)
        var Name_Text=findViewById<TextView>(R.id.Restaurant)
        var Category_Text=findViewById<TextView>(R.id.Category)
        var foodsList = ArrayList<Food>()

        foodsList.add(Food("아메리카노",R.drawable.americano,3000))
        foodsList.add(Food("카페라떼",R.drawable.ratte,4000))
        foodsList.add(Food("카페모카",R.drawable.mocha,4500))
        val adapter = MenuAdapter(this, foodsList)
        Menu_Grid.adapter = adapter
        Name_Text.text="스타버억"
        Category_Text.text="카페"
    }
}
