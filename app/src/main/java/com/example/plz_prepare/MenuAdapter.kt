package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage

class MenuAdapter(val ctxt : Context, val layoutId : Int, val foodsList : List<Menu> , val uid : String)
: ArrayAdapter<Menu>(ctxt,layoutId,foodsList){
    // 레스토랑의 메뉴에 대한 정보를 RestaurantActivity에 있는 리스트뷰에 연결하는 어뎁터
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val storageRef = FirebaseStorage.getInstance().getReference(uid+"/"+foodsList[position].Fname)
        GlideApp.with(view).load(storageRef).into(view.findViewById(R.id.menu_imageview)) // GlideApp을 이용하여 뷰에 있는 이미지뷰에 Firebase Storage에 있는 이미지를 load한다.
        val Fname = view.findViewById<TextView>(R.id.menu_name)
        val Fprice = view.findViewById<TextView>(R.id.menu_price)
        val food = foodsList[position]
        Fname.text=food.Fname
        Fprice.text=food.Fprice.toString()
        return view
    }
}