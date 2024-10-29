package com.example.wallet

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class loadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen) // Inflate the layout first

        val myView = findViewById<View>(R.id.main) // Find the view
        myView?.setOnApplyWindowInsetsListener { view, insets ->
            // Handle window insets
            insets
        }
    }
}