package com.example.plz_prepare


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_choice_restaurant.*


class ChoiceRestaurantActivity : AppCompatActivity() {

    val CP by lazy { intent.extras!!["CategoryPosition"] as Int }
    private lateinit var database : DatabaseReference
    lateinit var RestaurantList:MutableList<Restaurant>
    lateinit var uidList:MutableList<String?>
    lateinit var listView : ListView
    val CList : Array<String> = arrayOf("한식","중식","일식","양식","패스트푸드","카페")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_restaurant)

        order_.setOnClickListener {
            val nextIntent = Intent(this, OrderStateActivity::class.java)
            startActivity(nextIntent)
        }

        location_icon.setOnClickListener {
            Intent(this, LocationActivity::class.java)
        }

        RestaurantList = mutableListOf()
        uidList = mutableListOf()
        database=FirebaseDatabase.getInstance().reference.child("Users").child(CList[CP])
        listView = findViewById(R.id.restraunt_list)

        when(CP){
            0 -> category_1.setBackgroundColor(Color.parseColor("#80FF0000"))
            1 -> category_2.setBackgroundColor(Color.parseColor("#80FF0000"))
            2 -> category_3.setBackgroundColor(Color.parseColor("#80FF0000"))
            3 -> category_4.setBackgroundColor(Color.parseColor("#80FF0000"))
            4 -> category_5.setBackgroundColor(Color.parseColor("#80FF0000"))
            5 -> category_6.setBackgroundColor(Color.parseColor("#80FF0000"))
        }

        var c1 = findViewById<TextView>(R.id.category_1)
        var c2 = findViewById<TextView>(R.id.category_2)
        var c3 = findViewById<TextView>(R.id.category_3)
        var c4 = findViewById<TextView>(R.id.category_4)
        var c5 = findViewById<TextView>(R.id.category_5)
        var c6 = findViewById<TextView>(R.id.category_6)

        c1.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",0)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
        c2.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",1)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
        c3.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",2)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
        c4.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",3)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
        c5.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",4)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
        c6.setOnClickListener{
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("CategoryPosition",5)
            setResult(2,intent)
            startActivityForResult(intent,2)
        }
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
                            uidList.add(e.key)
                        }
                    }
                    val adapter = RestaurantListAdapter(this@ChoiceRestaurantActivity,R.layout.restaurant,RestaurantList,uidList)
                    listView.adapter = adapter
                }
            }
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,RestaurantActivity::class.java)
            intent.putExtra("Category",CList[CP])
            intent.putExtra("uid",uidList[position])
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1&&resultCode==1&&data!=null){
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("NewRoute",data.extras!!.get("NewRoute") as CheckingRoute)
            setResult(1,intent)
            finish()
        }
        if(requestCode==2&&resultCode==2&&data!=null){
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("NewRoute",data.extras!!.get("NewRoute") as CheckingRoute)
            setResult(1,intent)
            finish()
        }
    }
}