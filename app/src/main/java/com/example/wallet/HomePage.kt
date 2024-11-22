package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Gunakan padding XML sebagai dasar
            val defaultPaddingLeft = view.paddingLeft
            val defaultPaddingTop = view.paddingTop
            val defaultPaddingRight = view.paddingRight
            val defaultPaddingBottom = view.paddingBottom

            
            // Tambahkan padding dari WindowInsets
            view.setPadding(
                defaultPaddingLeft + systemBarsInsets.left,
                defaultPaddingTop + systemBarsInsets.top,
                defaultPaddingRight + systemBarsInsets.right,
                defaultPaddingBottom + systemBarsInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
        findViewById<View>(R.id.img_profile).setOnClickListener {
            val intent = Intent(this, editprofilePage::class.java)
            startActivity(intent)
        }
        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)
    }
}