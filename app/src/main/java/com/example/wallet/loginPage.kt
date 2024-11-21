package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class loginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val myLayout = findViewById<View>(R.id.main)
        myLayout?.setOnApplyWindowInsetsListener { view, insets ->
            view.onApplyWindowInsets(insets)
        }

        // Tombol Login
        val btnLogin = findViewById<View>(R.id.btn_login)
        btnLogin.setOnClickListener {
            // Pindah ke homePage
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        // TextView Daftar
        val tvDaftar = findViewById<View>(R.id.tv_daftar)
        tvDaftar.setOnClickListener {
            // Pindah ke signUpPage
            val intent = Intent(this, signupPage::class.java)
            startActivity(intent)
        }
    }
}
