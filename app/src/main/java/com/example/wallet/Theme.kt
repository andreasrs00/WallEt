package com.example.wallet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Theme : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_theme)

        // Mengatur padding untuk sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backarrorw: ImageView = findViewById(R.id.backArrow)

        backarrorw.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }



        val switchOn: Switch = findViewById(R.id.switch_on)
        val switchOff: Switch = findViewById(R.id.switch_off)
        val tvOn: TextView = findViewById(R.id.tv_on)
        val tvOff: TextView = findViewById(R.id.tv_off)



        // Cek preferensi mode gelap atau terang yang tersimpan
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (isDarkMode) {
            // Jika mode gelap, aktifkan switch On
            switchOn.isChecked = true
            switchOff.isChecked = false
        } else {
            // Jika mode terang, aktifkan switch Off
            switchOn.isChecked = false
            switchOff.isChecked = true
        }

        // Handle switch On clicked
        switchOn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Set Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                // Matikan switch Off dan update UI
                switchOff.isChecked = false
                switchOff.isEnabled = false
                switchOn.isChecked = true
                switchOn.isEnabled = true
                tvOn.text = "On"
                tvOff.text = "Off"
            } else {
                // Set Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                // Matikan switch On dan update UI
                switchOff.isChecked = true
                switchOff.isEnabled = true
                switchOn.isChecked = false
                switchOn.isEnabled = false
                tvOn.text = "On"
                tvOff.text = "Off"
            }
        }

        // Handle switch Off clicked
        switchOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Set Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                // Matikan switch On dan update UI
                switchOn.isChecked = false
                switchOn.isEnabled = false
                switchOff.isChecked = true
                switchOff.isEnabled = true
                tvOff.text = "Off"
                tvOn.text = "On"
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                // Matikan switch Off dan update UI
                switchOff.isChecked = false
                switchOff.isEnabled = false
                switchOn.isChecked = true
                switchOn.isEnabled = true
                tvOn.text = "On"
                tvOff.text = "Off"
            }
        }
    }
}
