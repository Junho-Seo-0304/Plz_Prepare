package com.example.plz_prepare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class CheckingLogin : AppCompatActivity() {
    // 어플을 시작할 때 익명 로그인을 하기위해 처음 뜨는 Activity
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_login)
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if (user != null) {
            // 이미 익명 로그인이 되어있으면 MainActivity로 넘어간다.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // 익명 로그인이 안되어있으면 익명으로 로그인 한다.
           signIn()
        }
    }

    private fun signIn(){ // 익명으로 로그인을 해주는 함수
        mAuth = FirebaseAuth.getInstance()
        mAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                // Firebase Auth의 signInAnonymously를 이용하여 익명 로그인을 한다.
                // 익명 로그인을 하는 이유는 유저의 UID를 이용하기 위해서
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    signIn()
                }
            }
    }
}

