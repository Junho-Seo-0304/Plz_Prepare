package com.example.plz_prepare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*

class RejectedReasonActivity : AppCompatActivity() {

    val route by lazy {intent.extras!!["Route"] as CheckingRoute}
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rejected_reason)
        database=FirebaseDatabase.getInstance().reference.child("Users").child(route.Category.toString()).child(route.Uid.toString()).child("RejectedOrder").child(route.Number.toString())
        val reason = findViewById<TextView>(R.id.reasonText)
        val okBtn = findViewById<Button>(R.id.okBtn)
        database.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                reason.text=p0.child("Reason").value.toString()
            }
        })
        okBtn.setOnClickListener {
            database.removeValue()
            finish()
        }

    }
}
