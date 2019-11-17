package com.example.plz_prepare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.plz_prepare.CheckingRoute
import com.example.plz_prepare.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OrderStateMenuAdapter(val ctxt : Context, val layoutId : Int, val RouteList : ArrayList<CheckingRoute>)
    : ArrayAdapter<CheckingRoute>(ctxt,layoutId,RouteList){

    private lateinit var firebase : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    var CheckDelete = false

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val Rname = view.findViewById<TextView>(R.id.order_state_res_name)
        val Orderlist = view.findViewById<TextView>(R.id.menu_num_list)
        val Number = view.findViewById<TextView>(R.id.number)
        val StateText = view.findViewById<TextView>(R.id.state_text)
        val Cancelbtn = view.findViewById<Button>(R.id.cancel_button)
        var state : String? = null
        firebase=FirebaseDatabase.getInstance().reference.child("Users").child(RouteList[position].Category!!).child(RouteList[position].Uid!!)
        mAuth = FirebaseAuth.getInstance()
        firebase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (CheckDelete==false) {
                    if (p0.child("PermissionOrder").child(RouteList[position].Number.toString()).child("Customer"
                        ).value == mAuth.currentUser!!.uid
                    ) {
                        Rname.text = p0.child("rname").value.toString()
                        StateText.text = "수락대기중"
                        Number.text = RouteList[position].Number.toString()
                        var totalString = ""
                        for (i in 1 until p0.child("PermissionOrder").child(RouteList[position].Number.toString()).childrenCount) {
                            totalString += p0.child("PermissionOrder").child(RouteList[position].Number.toString()).child(
                                (i - 1).toString()
                            ).child("food").child("fname").value.toString() + " : " + p0.child("PermissionOrder").child(
                                RouteList[position].Number.toString()
                            ).child((i - 1).toString()).child("num").value.toString() + "\n"
                        }
                        Orderlist.text = totalString
                        state = "Permission"
                    } else if (p0.child("ReadyOrder").child(RouteList[position].Number.toString()).child(
                            "Customer"
                        ).value == mAuth.currentUser!!.uid
                    ) {
                        Rname.text = p0.child("rname").value.toString()
                        StateText.text = "수락 완료"
                        Number.text = RouteList[position].Number.toString()
                        var totalString = ""
                        for (i in 1 until p0.child("ReadyOrder").child(RouteList[position].Number.toString()).childrenCount - 1) {
                            totalString += p0.child("ReadyOrder").child(RouteList[position].Number.toString()).child(
                                (i - 1).toString()
                            ).child("food").child("fname").toString() + " : " + p0.child("ReadyOrder").child(
                                RouteList[position].Number.toString()
                            ).child((i - 1).toString()).child("num").toString() + "\n"
                        }
                        Orderlist.text = totalString
                        state = "Ready"
                    } else {
                        RouteList.drop(position)
                    }
                }
            }
        })

        Cancelbtn.setOnClickListener {
            if(state=="Permission"){
                firebase.child("PermissionOrder").child(RouteList[position].Number.toString()).removeValue()
                RouteList.removeAt(position)
                notifyDataSetChanged()
                CheckDelete=true
            }
            if(state=="Ready"){
                firebase.child("ReadyOrder").child(RouteList[position].Number.toString()).removeValue()
                RouteList.removeAt(position)
                notifyDataSetChanged()
                CheckDelete=true
            }
        }

        return view
    }
}