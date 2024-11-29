package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class loadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val myView = findViewById<View>(R.id.main)
        myView?.setOnApplyWindowInsetsListener { view, insets ->
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, landingPage::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
}
