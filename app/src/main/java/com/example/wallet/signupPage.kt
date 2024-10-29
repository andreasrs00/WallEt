package com.example.wallet

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class signupPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)
        val myView = findViewById<View>(R.id.main) // Find the view
        myView?.setOnApplyWindowInsetsListener { view, insets ->
            // Handle window insets
            insets
        }
    }
}