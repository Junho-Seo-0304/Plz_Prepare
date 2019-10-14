package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.category.view.*

class CategoryAdapter(val context : Context, val category : ArrayList<Category>) : BaseAdapter(){
    override fun getCount(): Int {
        return category.size
    }

    override fun getItem(position: Int): Any {
        return category[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.category,parent,false) as View
        view.imgCategory.setImageResource(category[position].Cimg)
        view.findViewById<TextView>(R.id.categoryName).text = category[position].Cname
        return view
    }
}