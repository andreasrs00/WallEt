package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class landingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_page)

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tambahkan listener ke tombol
        findViewById<View>(R.id.btn_daftar).setOnClickListener {
            val intent = Intent(this, signupPage::class.java)
            startActivity(intent)
        }

        // Tambahkan listener ke teks
        findViewById<View>(R.id.tv_masuk).setOnClickListener {
            val intent = Intent(this, loginPage::class.java)
            startActivity(intent)
        }
    }
}
