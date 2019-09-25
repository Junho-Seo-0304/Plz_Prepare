package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.menu.view.*

class MenuAdapter(val context: Context,val foodslist:ArrayList<Food>) : BaseAdapter(){
    override fun getCount(): Int {
       return foodslist.size
    }

    override fun getItem(position: Int): Any {
        return foodslist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.menu,parent,false) as View
        view.imgFood.setImageResource(foodslist[position].image)
        view.findViewById<TextView>(R.id.menuName).text = foodslist[position].name
        view.findViewById<TextView>(R.id.menuPrice).text = foodslist[position].price.toString() + "원"
        return view
    }
}