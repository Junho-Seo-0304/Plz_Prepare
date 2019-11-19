package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RestaurantListAdapter(val ctxt : Context, val layoutId : Int, val restaurantList : ArrayList<Restaurant> , val uidList : ArrayList<String>)
    :ArrayAdapter<Restaurant>(ctxt,layoutId,restaurantList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        var storageRef = FirebaseStorage.getInstance().getReference(uidList[position]+"/"+"logo")
        GlideApp.with(view).load(storageRef).into(view.findViewById(R.id.Restaurant_imageview))
        val Rname = view.findViewById<TextView>(R.id.Restaurant_name)
        val restaurant = restaurantList[position]
        Rname.text=restaurant.Rname
        return view
    }
}