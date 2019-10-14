package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.firebase.database.FirebaseDatabase

class RestaurantListAdapter(val ctxt : Context, val layoutId : Int, val restaurantList : List<Restaurant>)
    :ArrayAdapter<Restaurant>(ctxt,layoutId,restaurantList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val Rname = view.findViewById<TextView>(R.id.Restaurant_name)
        val restaurant = restaurantList[position]
        Rname.text=restaurant.rname
        return view
    }
}