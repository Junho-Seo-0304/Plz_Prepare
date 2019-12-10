package com.example.plz_prepare

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderStateActivity: AppCompatActivity() {


    private lateinit var database : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    var changeRoute = arrayListOf<CheckingRoute>()
    var Menulist = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_state)
        database = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val CheckingList = findViewById<ListView>(R.id.order_state_menu_list)
        searchOrder(CheckingList)
    }

    private fun searchOrder(checkingList : ListView){ // 현 유저의 UID가 주문한 주문내역을 찾아 리스트뷰에 연결하는 함수
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                changeRoute.clear()
                Menulist.clear()
                for(DBcategory in p0.children){
                    for(DBrestaurant in DBcategory.children){
                        for(Pnum in DBrestaurant.child("PermissionOrder").children){
                            if (Pnum.child("Customer").value==mAuth.currentUser!!.uid){
                                changeRoute.add(CheckingRoute(DBcategory.key.toString(),DBrestaurant.key.toString(),Pnum.key.toString(),"PermissionOrder"))
                                var totalString = ""
                                for(i in 1 until Pnum.childrenCount-1){
                                    totalString += Pnum.child((i-1).toString()).child("food").child("fname").value.toString()+" : "+Pnum.child((i-1).toString()).child("num").value.toString()+"\n"
                                }
                                Menulist.add(totalString)
                            }
                        }
                        for(Rnum in DBrestaurant.child("ReadyOrder").children){
                            if (Rnum.child("Customer").value==mAuth.currentUser!!.uid){
                                changeRoute.add(CheckingRoute(DBcategory.key.toString(),DBrestaurant.key.toString(),Rnum.key.toString(),"ReadyOrder"))
                                var totalString = ""
                                val hour = Rnum.child("Time").child("Hour").value.toString()
                                val minute = Rnum.child("Time").child("Minute").value.toString()
                                totalString += hour+"시 "+minute+"분까지 준비 예정\n"
                                for(i in 1 until Rnum.childrenCount-2){
                                    totalString += Rnum.child((i-1).toString()).child("food").child("fname").value.toString()+" : "+Rnum.child((i-1).toString()).child("num").value.toString()+"\n"
                                }
                                Menulist.add(totalString)
                            }
                        }
                        for(RJnum in DBrestaurant.child("RejectedOrder").children){
                            if (RJnum.child("Customer").value==mAuth.currentUser!!.uid){
                                changeRoute.add(CheckingRoute(DBcategory.key.toString(),DBrestaurant.key.toString(),RJnum.key.toString(),"RejectedOrder"))
                                var totalString = ""
                                for(i in 1 until RJnum.childrenCount-1){
                                    totalString += RJnum.child((i-1).toString()).child("food").child("fname").value.toString()+" : "+RJnum.child((i-1).toString()).child("num").value.toString()+"\n"
                                }
                                Menulist.add(totalString)
                            }
                        }
                    }
                }
                checkingList.adapter = OrderStateMenuAdapter(baseContext,R.layout.order_state_menu,changeRoute,Menulist)
            }
        })
    }
}