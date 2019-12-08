package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val categoryList = ArrayList<Category>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Cgrid = findViewById<GridView>(R.id.category_gridview)
        categoryList.add(Category("한식", R.drawable.korean))
        categoryList.add(Category("중식",R.drawable.chinese))
        categoryList.add(Category("일식",R.drawable.japenese))
        categoryList.add(Category("양식",R.drawable.western))
        categoryList.add(Category("패스트푸드",R.drawable.junk))
        categoryList.add(Category("카페",R.drawable.cafe))
        val CAdapter = CategoryAdapter(this,categoryList)
        Cgrid.adapter=CAdapter

        val order_ = findViewById<ImageView>(R.id.order_)

        Cgrid.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",position)
            startActivity(intent)
        }

        order_.setOnClickListener {
            val intent = Intent(this, OrderStateActivity::class.java)
            startActivity(intent)
        }

    }
}
