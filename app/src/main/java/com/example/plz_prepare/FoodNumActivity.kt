package com.example.plz_prepare

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_food_num.*

class FoodNumActivity : AppCompatActivity() {

    val food by lazy {intent.extras!!["Food"] as Menu}
    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val pushed by lazy { intent.extras!!["Pushed"] as Int }
    var num : Int = 0
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food_num)
        var imgView = findViewById<ImageView>(R.id.imgFood)
        var foodName = findViewById<TextView>(R.id.foodName)
        var foodPrice = findViewById<TextView>(R.id.foodPrice)
        var foodExplain = findViewById<TextView>(R.id.foodExplain)
        var foodQuantity = findViewById<TextView>(R.id.num)
        var minusButton = findViewById<ImageView>(R.id.minus_button)
        var plusButton = findViewById<ImageView>(R.id.plus_button)
        var button = findViewById<Button>(R.id.button)
        var cashBtn = findViewById<Button>(R.id.cash)

        var storageRef = FirebaseStorage.getInstance().getReference(uid+"/"+food.Fname)
        GlideApp.with(this).load(storageRef).into(imgView)
        foodName.text=food.Fname
        foodPrice.text=food.Fprice.toString()
        foodExplain.text=food.Fexplain
        foodQuantity.text=num.toString()

        button.setOnClickListener{
            if(num>0) {
                val numIntent = Intent(this, RestaurantActivity::class.java)
                var order = Order(food, num)
                numIntent.putExtra("Order", order)
                setResult(1, numIntent)
                finish()
            } else{
                Toast.makeText(this,"주문할 수량을 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        cashBtn.setOnClickListener {
            if(num>0) {
                val intent = Intent(this, BasketActivity::class.java)
                var orderList = arrayListOf<Order>()
                orderList.add(Order(food, num))
                intent.putExtra("Category", category)
                intent.putExtra("uid", uid)
                intent.putExtra("OrderList", orderList)
                intent.putExtra("Pushed", pushed)
                startActivityForResult(intent, 2)
            } else{
                Toast.makeText(this,"주문할 수량을 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        minusButton.setOnClickListener {
            if(num!=0){
                num--
            }
            foodQuantity.text=num.toString()
        }

        plusButton.setOnClickListener {
            num++
            foodQuantity.text=num.toString()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2&&resultCode==2&&data!=null){
            val intent = Intent(this,RestaurantActivity::class.java)
            intent.putExtra("NewRoute",data.extras!!.get("NewRoute") as CheckingRoute)
            setResult(2,intent)
            finish()
        }
    }
}
