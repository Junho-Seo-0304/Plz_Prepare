package com.example.plz_prepare


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_choice_restaurant.*


class ChoiceRestaurantActivity : AppCompatActivity(){

    val CP by lazy { intent.extras!!["CategoryPosition"] as Int }
    private lateinit var database : DatabaseReference
    var RestaurantList = arrayListOf<Restaurant>()
    var uidList = arrayListOf<String>()
    val CList : Array<String> = arrayOf("한식","중식","일식","양식","패스트푸드","카페")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_restaurant)

        val locationBar = findViewById<TextView>(R.id.location_bar)

        locationBar.text = "지도로 식당 위치 보기"
        locationBar.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivityForResult(intent,2)
        }


        var category = CList[CP]
        val listView = findViewById<ListView>(R.id.restraunt_list)


        val c1 = findViewById<TextView>(R.id.category_1)
        val c2 = findViewById<TextView>(R.id.category_2)
        val c3 = findViewById<TextView>(R.id.category_3)
        val c4 = findViewById<TextView>(R.id.category_4)
        val c5 = findViewById<TextView>(R.id.category_5)
        val c6 = findViewById<TextView>(R.id.category_6)

        changeColor(category)
        changeListView(category,listView)
        c1.setOnClickListener{
            category="한식"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }
        c2.setOnClickListener{
            category="중식"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }
        c3.setOnClickListener{
            category="일식"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }
        c4.setOnClickListener{
            category="양식"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }
        c5.setOnClickListener{
            category="패스트푸드"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }
        c6.setOnClickListener{
            category="카페"
            resetColor()
            changeColor(category)
            changeListView(category,listView)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,RestaurantActivity::class.java)
            intent.putExtra("Category",category)
            intent.putExtra("uid",uidList[position])
            startActivity(intent)
            finish()
        }
    }

    private fun resetColor(){
        category_1.setBackgroundColor(Color.parseColor("#A9EB8282"))
        category_2.setBackgroundColor(Color.parseColor("#A9EB8282"))
        category_3.setBackgroundColor(Color.parseColor("#A9EB8282"))
        category_4.setBackgroundColor(Color.parseColor("#A9EB8282"))
        category_5.setBackgroundColor(Color.parseColor("#A9EB8282"))
        category_6.setBackgroundColor(Color.parseColor("#A9EB8282"))
    }

    private fun changeColor(category: String){
        when(category){
            "한식" -> category_1.setBackgroundColor(Color.parseColor("#80FF0000"))
            "중식" -> category_2.setBackgroundColor(Color.parseColor("#80FF0000"))
            "일식" -> category_3.setBackgroundColor(Color.parseColor("#80FF0000"))
            "양식" -> category_4.setBackgroundColor(Color.parseColor("#80FF0000"))
            "패스트푸드" -> category_5.setBackgroundColor(Color.parseColor("#80FF0000"))
            "카페" -> category_6.setBackgroundColor(Color.parseColor("#80FF0000"))
        }
    }

    private fun changeListView(category: String, listView : ListView){
        database=FirebaseDatabase.getInstance().reference.child("Users").child(category)
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    RestaurantList.clear()
                    uidList.clear()
                    for(e in p0.children){
                        if(e.exists()) {
                            val restaurant = e.getValue(Restaurant::class.java)!!
                            RestaurantList.add(restaurant)
                            uidList.add(e.key.toString())
                        }
                    }
                    val adapter = RestaurantListAdapter(this@ChoiceRestaurantActivity,R.layout.restaurant,RestaurantList,uidList,category)
                    listView.adapter = adapter
                }
            }
        })
    }
}