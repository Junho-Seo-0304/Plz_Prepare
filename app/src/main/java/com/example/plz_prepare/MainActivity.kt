package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_food_num.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    val categoryList = ArrayList<Category>()
    var routeArrayList = arrayListOf<CheckingRoute>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Cgrid = findViewById<GridView>(R.id.category_gridview)
        categoryList.add(Category("한식", R.drawable.korean))
        categoryList.add(Category("중식",R.drawable.chinese))
        categoryList.add(Category("일식",R.drawable.japenese))
        categoryList.add(Category("양식",R.drawable.western))
        categoryList.add(Category("패스트푸드",R.drawable.junk))
        categoryList.add(Category("카페",R.drawable.cafe))
        val CAdapter = CategoryAdapter(this,categoryList)
        Cgrid.adapter=CAdapter

        var order_ = findViewById<ImageView>(R.id.order_)

        Cgrid.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",position)
            startActivityForResult(intent,1)
        }

        order_.setOnClickListener {
            val intent = Intent(this, OrderStateActivity::class.java)
            intent.putExtra("Route", routeArrayList)
            startActivityForResult(intent, 2)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1&&resultCode==1&&data!=null){
            val newRoute = data.extras!!.get("NewRoute") as CheckingRoute
            routeArrayList.add(newRoute)
        }
        if(requestCode==2&&resultCode==2&&data!=null){
            val newRoute = data.extras!!.get("NewRoute") as ArrayList<CheckingRoute>
            routeArrayList = newRoute
        }
    }
}
