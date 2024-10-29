package com.example.wallet

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
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
    }
}