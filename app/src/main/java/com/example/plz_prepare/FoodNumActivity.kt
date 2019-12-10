package com.example.plz_prepare


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.storage.FirebaseStorage

class FoodNumActivity : AppCompatActivity() {

    val food by lazy {intent.extras!!["Food"] as Menu}
    val category by lazy { intent.extras!!["Category"] as String }
    val uid by lazy {intent.extras!!["uid"] as String}
    val pushed by lazy { intent.extras!!["Pushed"] as Int }
    var num : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food_num)
        val imgView = findViewById<ImageView>(R.id.imgFood)
        val foodName = findViewById<TextView>(R.id.foodName)
        val foodPrice = findViewById<TextView>(R.id.foodPrice)
        val foodExplain = findViewById<TextView>(R.id.foodExplain)
        val foodQuantity = findViewById<TextView>(R.id.num)
        val minusButton = findViewById<ImageView>(R.id.minus_button)
        val plusButton = findViewById<ImageView>(R.id.plus_button)
        val button = findViewById<Button>(R.id.button)
        val cashBtn = findViewById<Button>(R.id.cash)

        val storageRef = FirebaseStorage.getInstance().getReference(uid+"/"+food.Fname)
        GlideApp.with(this).load(storageRef).into(imgView) // GlideApp을 이용해 Firebase Storage에 저장되어있는 음식 이미지를 이미지뷰에 load해준다.
        foodName.text=food.Fname
        foodPrice.text=food.Fprice.toString()
        foodExplain.text=food.Fexplain
        foodQuantity.text=num.toString()

        button.setOnClickListener{
            // 주문할 음식의 수가 0 이상일때만 장바구니에 넣을 수 있다
            if(num>0) {
                val numIntent = Intent(this, RestaurantActivity::class.java)
                val order = Order(food, num)
                numIntent.putExtra("Order", order)
                setResult(1, numIntent)
                finish()
            } else{
                Toast.makeText(this,"주문할 수량을 확인해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        cashBtn.setOnClickListener {
            // 주문할 음식의 수가 0 이상일때만 주문할 수 있다.
            if(num>0) {
                val intent = Intent(this, BasketActivity::class.java)
                val orderList = arrayListOf<Order>()
                orderList.add(Order(food, num))
                intent.putExtra("Category", category)
                intent.putExtra("uid", uid)
                intent.putExtra("Pushed",pushed)
                intent.putExtra("OrderList", orderList)
                startActivityForResult(intent, 1)
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
        if (requestCode==1&&resultCode==2&&data!=null){
            val intent = Intent(this,RestaurantActivity::class.java)
            setResult(2,intent)
            finish()
        }
    }
}
