package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class loadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen) // Inflate the layout first

        // Timer untuk menunda perpindahan halaman
        Handler(Looper.getMainLooper()).postDelayed({
            // Pindah ke LandingPage
            val intent = Intent(this, landingPage::class.java)
            startActivity(intent)
            finish() // Tutup activity loadingScreen agar tidak bisa kembali dengan tombol back
        }, 500) // 3000 ms = 3 detik
    }
}
