package com.example.plz_prepare


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId

class BasketActivity : AppCompatActivity() {

    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val orderList by lazy { intent.extras!!["OrderList"] as ArrayList<Order> }
    val pushed by lazy { intent.extras!!["Pushed"] as Int }
    private lateinit var database : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    private lateinit var listView : ListView
    var Complete = false
    var number = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        database = FirebaseDatabase.getInstance().reference.child("Users").child(category).child(uid)
        mAuth = FirebaseAuth.getInstance()
        listView = findViewById(R.id.basket_menu_list)
        listView.adapter = BasketAdapter(this,R.layout.basketed_menu,orderList,uid)
        val cashBtn = findViewById<Button>(R.id.cash_button)

        cashBtn.setOnClickListener {
            payment()
        }
    }

    private fun payment(){ // 결제 하고 주문 내역을 Firebase realtime database에 저장
        var totalNum = 0
        for (i in orderList) {
            totalNum += i.num
        }
        if (totalNum > 0) {
            database.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    // 주문할려는 레스토랑에 준비할 주문이 있으면 현재 사용된 주문 번호 다음 번호를 이용한다.
                    // 주문할려는 레스토랑에 준비할 주문이 없으면 101번을 이용
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
                            if(p0.hasChild("PushKey")) {
                                val pushKey = p0.child("PushKey").value.toString()
                                Thread {
                                    FCMPush(pushKey).pushFCMNotification()
                                }.start()
                            }
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
                                if(p0.hasChild("PushKey")) {
                                    val pushKey = p0.child("PushKey").value.toString()
                                    Thread {
                                        FCMPush(pushKey).pushFCMNotification()
                                    }.start()
                                }
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
                                if(p0.hasChild("PushKey")) {
                                    val pushKey = p0.child("PushKey").value.toString()
                                    Thread {
                                        FCMPush(pushKey).pushFCMNotification()
                                    }.start()
                                }
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