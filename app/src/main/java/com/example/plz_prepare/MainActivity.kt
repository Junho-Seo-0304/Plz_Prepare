package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
    var Categorysgrid=findViewById<GridView>(R.id.category_gridview)
    var Location_bar=findViewById<TextView>(R.id.location_bar)
    var CategorysList = arrayListOf<Category>(
        Category(R.drawable.korean,"한식"),
        Category(R.drawable.western,"양식"),
        Category(R.drawable.japenese,"일식"),
        Category(R.drawable.chinese,"중식"),
        Category(R.drawable.festfood,"패스트푸드"),
        Category(R.drawable.cafe,"카페/디저트")
    )
    var order_img=findViewById<ImageView>(R.id.order_)
    var Location_icon=findViewById<ImageView>(R.id.location_icon)


    val C_adapter = CategoryAdapter(this, CategorysList)
    Categorysgrid.adapter = C_adapter

    category_gridview.setOnItemClickListener { parent, view, position, id ->
        val categoryIntent = Intent(this,ChoiceRestaurantActivity::class.java)
        categoryIntent.putExtra("Category",CategorysList[position])
        startActivityForResult(categoryIntent,1)
    }
}
