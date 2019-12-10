package com.example.plz_prepare

import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList

class RestaurantListAdapter(val ctxt : Context, val layoutId : Int, val restaurantList : ArrayList<Restaurant> , val uidList : ArrayList<String>, val category: String)
    :ArrayAdapter<Restaurant>(ctxt,layoutId,restaurantList){
    // ChoiceRestaurantActivity에 레스토랑들을 리스트뷰에 연결해주는 어뎁터
    private lateinit var firebase : DatabaseReference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        firebase=FirebaseDatabase.getInstance().reference.child("Users").child(category).child(uidList[position])
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val storageRef = FirebaseStorage.getInstance().getReference(uidList[position]+"/"+"logo")
        GlideApp.with(view).load(storageRef).into(view.findViewById(R.id.Restaurant_imageview))
        val Rname = view.findViewById<TextView>(R.id.Restaurant_name)
        val locationText = view.findViewById<TextView>(R.id.location_info)
        firebase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                val rlX = p0.child("rlocationX").value.toString().toDouble()
                val rlY = p0.child("rlocationY").value.toString().toDouble()
                // geocoder를 이용하여 레스토랑의 위치를 주소로 바꿔준다.
                val geocoder = Geocoder(ctxt, Locale.KOREAN)
                val location = geocoder.getFromLocation(rlX,rlY,1)
                locationText.text = location[0].getAddressLine(0)
            }
        })
        val restaurant = restaurantList[position]
        Rname.text=restaurant.Rname
        return view
    }
}