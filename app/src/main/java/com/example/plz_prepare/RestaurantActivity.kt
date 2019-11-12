package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_food_num.*
import kotlinx.android.synthetic.main.activity_restaurant.*
import java.util.*
import kotlin.collections.ArrayList

class RestaurantActivity : AppCompatActivity() {


    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    private lateinit var database : DatabaseReference
    lateinit var foodsList : MutableList<Menu>
    lateinit var listView : ListView
    var orderList = arrayListOf<Order>()
    var priceState = 0
    var pushBtn = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        database = FirebaseDatabase.getInstance().reference.child("Users").child(category).child(uid)
        listView = findViewById(R.id.menu_list)

        var Name_Text=findViewById<TextView>(R.id.order_bar)
        foodsList = mutableListOf()
        var button=findViewById<Button>(R.id.cash_button)


        database.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val restInfo = p0.getValue(Restaurant::class.java)
                    Name_Text.text = restInfo!!.Rname
                    foodsList.clear()
                    for(e in p0.child("Menu").children){
                        if(e.exists()) {
                            val food = e.getValue(Menu::class.java)!!
                            foodsList.add(food)
                        }
                    }
                    val adapter = MenuAdapter(this@RestaurantActivity,R.layout.menu,foodsList)
                    listView.adapter = adapter
                }
            }
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, FoodNumActivity::class.java)
            intent.putExtra("Food", foodsList[position])
            intent.putExtra("Category",category)
            intent.putExtra("uid",uid)
            pushBtn = 2
            intent.putExtra("Pushed",pushBtn)
            startActivityForResult(intent, 2)
        }


        button.text = priceState.toString() + "원 결제하기"

        button.setOnClickListener {
            val intent = Intent(this,BasketActivity::class.java)
            intent.putExtra("Category",category)
            intent.putExtra("uid",uid)
            intent.putExtra("OrderList",orderList)
            pushBtn = 1
            intent.putExtra("Pushed",pushBtn)
            startActivityForResult(intent,1)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1&&resultCode==1&&data!=null){
            val intent = Intent(this,ChoiceRestaurantActivity::class.java)
            intent.putExtra("NewRoute",data.extras!!.get("NewRoute") as CheckingRoute)
            setResult(1,intent)
            finish()
        }
            if(requestCode==2){
                if(resultCode==1&&data!=null) {
                    val newOrder = data.extras!!.get("Order") as Order
                    orderList.add(newOrder)
                    if(orderList.size!=0) {
                        for (e in orderList) {
                            priceState +=( e.food!!.Fprice!! * e.num)
                        }
                    }
                    cash_button.text = priceState.toString() + "원 결제하기"
                }
                if (resultCode==2&&data!=null){
                    val intent = Intent(this,ChoiceRestaurantActivity::class.java)
                    intent.putExtra("NewRoute",data.extras!!.get("NewRoute") as CheckingRoute)
                    setResult(1,intent)
                    finish()
                }
            }
        }
    }

