package com.example.plz_prepare

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_food_num.*
import kotlinx.android.synthetic.main.order_state_menu.*

class BasketActivity : AppCompatActivity() {

    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val orderList by lazy { intent.extras!!["OrderList"] as ArrayList<Order> }
    val pushed by lazy { intent.extras!!["Pushed"] as Int }
    private lateinit var database : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    lateinit var listView : ListView
    var Complete = false
    var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        database = FirebaseDatabase.getInstance().reference.child("Users").child(category).child(uid)
        mAuth = FirebaseAuth.getInstance()
        listView = findViewById(R.id.basket_menu_list)
        listView.adapter = BasketAdapter(this,R.layout.basketed_menu,orderList,uid)
        var cashBtn = findViewById<Button>(R.id.cash_button)

        cashBtn.setOnClickListener {
            var totalNum = 0
            for (i in orderList) {
                totalNum += i.num
            }
            if (totalNum > 0) {
                database.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        if (p0.child("PermissionOrder").exists()) {
                            if (Complete == false) {

                                var num: Int =
                                    Integer.parseInt(p0.child("UsedNum").value.toString())
                                num++
                                number = num
                                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
                                    val newToken = it.token
                                    database.child("PermissionOrder").child(num.toString())
                                        .child("PushKey").setValue(newToken)
                                }
                                database.child("PermissionOrder").child(num.toString())
                                    .setValue(orderList)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            baseContext,
                                            "신청이 완료 되었습니다. 주문 내역에서 결과를 확인해주세요.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                database.child("PermissionOrder").child(num.toString())
                                    .child("Customer").setValue(mAuth.currentUser!!.uid)
                                database.child("UsedNum").setValue(number)
                                Complete = true
                                if (pushed == 1) {
                                    val intent = Intent(baseContext, RestaurantActivity::class.java)
                                    setResult(1, intent)
                                    finish()
                                } else {
                                    val intent = Intent(baseContext, FoodNumActivity::class.java)
                                    setResult(2, intent)
                                    finish()
                                }
                            }
                        } else {
                            if (p0.child("ReadyOrder").exists()){
                                if (Complete == false) {

                                    var num: Int =
                                        Integer.parseInt(p0.child("UsedNum").value.toString())
                                    num++
                                    number = num
                                    FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
                                        val newToken = it.token
                                        database.child("PermissionOrder").child(num.toString())
                                            .child("PushKey").setValue(newToken)
                                    }
                                    database.child("PermissionOrder").child(num.toString())
                                        .setValue(orderList)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                baseContext,
                                                "신청이 완료 되었습니다. 주문 내역에서 결과를 확인해주세요.",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                    database.child("PermissionOrder").child(num.toString())
                                        .child("Customer").setValue(mAuth.currentUser!!.uid)
                                    database.child("UsedNum").setValue(number)
                                    Complete = true
                                    if (pushed == 1) {
                                        val intent = Intent(baseContext, RestaurantActivity::class.java)
                                        setResult(1, intent)
                                        finish()
                                    } else {
                                        val intent = Intent(baseContext, FoodNumActivity::class.java)
                                        setResult(2, intent)
                                        finish()
                                    }
                                }
                            }else{
                            if (Complete == false) {
                                FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
                                    val newToken = it.token
                                    database.child("PermissionOrder").child("101")
                                        .child("PushKey").setValue(newToken)
                                }
                                database.child("PermissionOrder").child("101").setValue(orderList)
                                    .addOnSuccessListener {
                                        Toast.makeText(
                                            baseContext,
                                            "신청이 완료 되었습니다. 주문 내역에서 결과를 확인해주세요.",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                database.child("PermissionOrder").child("101").child("Customer")
                                    .setValue(mAuth.currentUser!!.uid)
                                database.child("UsedNum").setValue(101)
                                number = 101
                                Complete = true
                                if (pushed == 1) {
                                    val intent = Intent(baseContext, RestaurantActivity::class.java)
                                    setResult(1, intent)
                                    finish()
                                } else {
                                    val intent = Intent(baseContext, FoodNumActivity::class.java)
                                    setResult(2, intent)
                                    finish()
                                }
                            }
                            }
                        }
                    }
                })
            } else {
                Toast.makeText(this,"주문할 음식이 없습니다.\n다시 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}