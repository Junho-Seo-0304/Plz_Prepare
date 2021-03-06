package com.example.plz_prepare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage

class BasketAdapter(val ctxt : Context, val layoutId : Int, val orderList : List<Order>, val uid : String)
    : ArrayAdapter<Order>(ctxt,layoutId,orderList){
    // 주문할 음식을 장바구니 Activity에 있는 리스트뷰와 연결을 해주는 어뎁터
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(ctxt)
        val view : View = layoutInflater.inflate(layoutId,null)
        val Fimg = view.findViewById<ImageView>(R.id.basket_menu_imageview)
        val Fname = view.findViewById<TextView>(R.id.basket_menu_name)
        val Fprice = view.findViewById<TextView>(R.id.basket_menu_price)
        val Fnum = view.findViewById<TextView>(R.id.basket_num)
        val Pbtn = view.findViewById<ImageView>(R.id.basket_plus_button)
        val Mbtn = view.findViewById<ImageView>(R.id.basket_minus_button)

        val storageRef = FirebaseStorage.getInstance().getReference(uid+"/"+orderList[position].food!!.Fname)
        GlideApp.with(view).load(storageRef).into(Fimg)
        Fname.text = orderList[position].food!!.Fname
        Fprice.text = orderList[position].food!!.Fprice.toString()
        Fnum.text = orderList[position].num.toString()

        Pbtn.setOnClickListener {
            // 주문 개수를 늘리는 버튼
            orderList[position].num++
            this.notifyDataSetChanged()
        }

        Mbtn.setOnClickListener {
            // 주문 개수를 줄이는 버튼
            if(orderList[position].num!=0){
                orderList[position].num--
                this.notifyDataSetChanged()
            }
        }

        return view
    }
}