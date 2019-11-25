package com.example.plz_prepare

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class OrderStateActivity: AppCompatActivity() {


    private lateinit var database : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    var checkRoute = arrayListOf<CheckingRoute>()
    var changeRoute = arrayListOf<CheckingRoute>()
    var Menulist = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_state)
        if(intent!=null){
            val routeArrayList by lazy { intent.extras!!["Route"] as ArrayList<CheckingRoute>}
            checkRoute=routeArrayList
        }
        database = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        var CheckingList = findViewById<ListView>(R.id.order_state_menu_list)
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                changeRoute.clear()
                Menulist.clear()
                for (i in 0 until checkRoute.size){
                    if(p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("PermissionOrder").child(checkRoute[i].Number.toString()).child("Customer").value==mAuth.currentUser!!.uid){
                        changeRoute.add(CheckingRoute(checkRoute[i].Category,checkRoute[i].Uid,checkRoute[i].Number,"PermissionOrder"))
                        var totalString = ""
                        for (j in 1 until p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("PermissionOrder").child(checkRoute[i].Number.toString()).childrenCount-1) {
                            totalString += p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("PermissionOrder").child(checkRoute[i].Number.toString()).child(
                                (j - 1).toString()
                            ).child("food").child("fname").value.toString() + " : " + p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("PermissionOrder").child(
                                checkRoute[i].Number.toString()
                            ).child((j - 1).toString()).child("num").value.toString() + "\n"
                        }
                        Menulist.add(totalString)
                    }
                    else if (p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(checkRoute[i].Number.toString()).child("Customer").value==mAuth.currentUser!!.uid){
                        changeRoute.add(CheckingRoute(checkRoute[i].Category,checkRoute[i].Uid,checkRoute[i].Number,"ReadyOrder"))
                        var totalString = p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(checkRoute[i].Number.toString()).child("Time").child("Hour").value.toString()+"시 "+p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(checkRoute[i].Number.toString()).child("Time").child("Minute").value.toString()+"분에 완료 예정\n"
                        for (j in 1 until p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(checkRoute[i].Number.toString()).childrenCount-2) {
                            totalString += p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(checkRoute[i].Number.toString()).child(
                                (j - 1).toString()
                            ).child("food").child("fname").value.toString() + " : " + p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("ReadyOrder").child(
                                checkRoute[i].Number.toString()
                            ).child((j - 1).toString()).child("num").value.toString() + "\n"
                        }
                        Menulist.add(totalString)
                    }
                    else if (p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("RejectedOrder").child(checkRoute[i].Number.toString()).child("Customer").value==mAuth.currentUser!!.uid){
                        changeRoute.add(CheckingRoute(checkRoute[i].Category,checkRoute[i].Uid,checkRoute[i].Number,"RejectedOrder"))
                        var totalString = ""
                        for (j in 1 until p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("RejectedOrder").child(checkRoute[i].Number.toString()).childrenCount) {
                            totalString += p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("RejectedOrder").child(checkRoute[i].Number.toString()).child(
                                (j - 1).toString()
                            ).child("food").child("fname").value.toString() + " : " + p0.child(checkRoute[i].Category.toString()).child(checkRoute[i].Uid.toString()).child("RejectedOrder").child(
                                checkRoute[i].Number.toString()
                            ).child((j - 1).toString()).child("num").value.toString() + "\n"
                        }
                        Menulist.add(totalString)
                    }
                }
                CheckingList.adapter = OrderStateMenuAdapter(baseContext,R.layout.order_state_menu,changeRoute,Menulist)
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("NewRoute",changeRoute)
        setResult(2,intent)
        super.onBackPressed()
    }
}