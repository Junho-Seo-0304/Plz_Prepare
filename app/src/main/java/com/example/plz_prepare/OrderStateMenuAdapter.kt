package com.example.plz_prepare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class OrderStateMenuAdapter(val ctxt : Context, val layoutId : Int, val RouteList : ArrayList<CheckingRoute>, val MenuList : ArrayList<String>)
    : ArrayAdapter<CheckingRoute>(ctxt,layoutId,RouteList){

    private lateinit var firebase : DatabaseReference
    private lateinit var mAuth : FirebaseAuth
    var phoneNum : String? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val logo = view.findViewById<ImageView>(R.id.order_state_res_imageview)
        val Rname = view.findViewById<TextView>(R.id.order_state_res_name)
        val Orderlist = view.findViewById<TextView>(R.id.menu_num_list)
        val Number = view.findViewById<TextView>(R.id.number)
        val StateText = view.findViewById<TextView>(R.id.state_text)
        val Cancelbtn = view.findViewById<Button>(R.id.cancel_button)

        val storageRef = FirebaseStorage.getInstance().getReference(RouteList[position].Uid+"/"+"logo")
        GlideApp.with(view).load(storageRef).into(logo)
        firebase=FirebaseDatabase.getInstance().reference.child("Users").child(RouteList[position].Category!!).child(RouteList[position].Uid!!)
        mAuth = FirebaseAuth.getInstance()
        firebase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                phoneNum = p0.child("phoneNum").value.toString()
                Rname.text = p0.child("rname").value.toString()
            }
        })
        if (RouteList[position].State=="PermissionOrder") {
            StateText.text = "수락대기중"
            Number.text = RouteList[position].Number
            Orderlist.text = MenuList[position]
        } else if (RouteList[position].State=="ReadyOrder") {
            StateText.text = "수락 완료"
            Number.text = RouteList[position].Number
            Orderlist.text = MenuList[position]
        } else if (RouteList[position].State=="RejectedOrder"){
            StateText.text = "주문 거절"
            Number.text = RouteList[position].Number
            Orderlist.text = "주문이 거절되었습니다."
            Cancelbtn.text = "거절 이유"
        }

        Cancelbtn.setOnClickListener {
            if(RouteList[position].State=="PermissionOrder"){
                firebase.child("PermissionOrder").child(RouteList[position].Number.toString()).removeValue()
            }
            if(RouteList[position].State=="ReadyOrder"){
                if(phoneNum==null) {
                    Toast.makeText(ctxt, "수락 완료된 주문은 취소 할 수 없습니다.", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(ctxt, "수락 완료된 주문은 취소 할 수 없습니다. \n " + phoneNum + "으로 전화해 요청해보세요.", Toast.LENGTH_LONG).show()
                }
            }
            if(RouteList[position].State=="RejectedOrder"){
                val intent = Intent(ctxt,RejectedReasonActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("Route",RouteList[position])
                ctxt.startActivity(intent)
            }
        }

        return view
    }
}