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
    var changeRoute = arrayListOf<CheckingRoute>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_state)
        if(intent!=null){
            val routeArrayList by lazy { intent.extras!!["Route"] as ArrayList<CheckingRoute>}
            changeRoute = routeArrayList
        }
        database = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.child(""))
            }
        })
        var CheckingList = findViewById<ListView>(R.id.order_state_menu_list)
        CheckingList.adapter = OrderStateMenuAdapter(this,R.layout.order_state_menu,changeRoute)
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("NewRoute",changeRoute)
        setResult(2,intent)
        super.onBackPressed()
    }
}