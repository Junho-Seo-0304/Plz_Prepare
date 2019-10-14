package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.category.view.*

class CategoryAdapter(val context: Context,val category_grid:ArrayList< Category>) : BaseAdapter(){
    override fun getCount(): Int {
        return category_grid.size
    }

    override fun getItem(position: Int): Any {
        return category_grid[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.menu,parent,false) as View
        view.imgCategory.setImageResource(category_grid[position].c_image)
        view.findViewById<TextView>(R.id.categoryName).text = category_grid[position].c_name
        return view
    }
}