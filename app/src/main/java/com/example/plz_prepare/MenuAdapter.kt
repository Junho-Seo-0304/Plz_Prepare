package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter

import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_food_num.view.*
import java.text.FieldPosition


class MenuAdapter(val ctxt : Context, val layoutId : Int, val foodsList : List<Menu> , val uid : String)
: ArrayAdapter<Menu>(ctxt,layoutId,foodsList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        var storageRef = FirebaseStorage.getInstance().getReference(uid+"/"+foodsList[position].Fname)
        GlideApp.with(view).load(storageRef).into(view.findViewById(R.id.menu_imageview))
        val Fname = view.findViewById<TextView>(R.id.menu_name)
        val Fprice = view.findViewById<TextView>(R.id.menu_price)
        val food = foodsList[position]
        Fname.text=food.Fname
        Fprice.text=food.Fprice.toString()
        return view
    }
}