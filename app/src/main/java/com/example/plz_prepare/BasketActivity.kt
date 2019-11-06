package com.example.plz_prepare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.order_state_menu.*

class BasketActivity : AppCompatActivity() {

    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val orderList by lazy { intent.extras!!["OrderList"] as ArrayList<Order> }
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
        listView.adapter = BasketAdapter(this,R.layout.basketed_menu,orderList)
        var cashBtn = findViewById<Button>(R.id.cash_button)

        cashBtn.setOnClickListener {

            database.addValueEventListener(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {

                    if(p0.child("PermissionOrder").exists()){
                        if(Complete==false) {
                            var num : Int = Integer.parseInt(p0.child("UsedNum").value.toString())
                            num++
                            number = num
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
                            Complete=true
                            val intent = Intent(baseContext,OrderStateActivity::class.java)
                            intent.putExtra("Category",category)
                            intent.putExtra("uid",uid)
                            intent.putExtra("Number",number)
                            startActivity(intent)
                            finish()
                        }
                    }
                    else {
                        if(Complete==false) {
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
                            val intent = Intent(baseContext, OrderStateActivity::class.java)
                            intent.putExtra("Category", category)
                            intent.putExtra("uid", uid)
                            intent.putExtra("Number", number)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            })
        }
    }
}