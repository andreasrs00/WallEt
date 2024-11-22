package com.example.wallet

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class NavBarComponent(context: Context, navbar: View) {
    init {
        // Menangani klik pada item navbar
        val homeButton: View = navbar.findViewById(R.id.nav_home)
        val transactionButton: View = navbar.findViewById(R.id.nav_transaction)
        val analysisButton: View = navbar.findViewById(R.id.nav_analysis)
        val categoriesButton: View = navbar.findViewById(R.id.nav_categories)
        val profileButton: View = navbar.findViewById(R.id.nav_profile)

        // Menambahkan OnClickListener pada setiap tombol navbar
        homeButton.setOnClickListener {
            val intent = Intent(context, HomePage::class.java)
            context.startActivity(intent)
        }

        transactionButton.setOnClickListener {
            val intent = Intent(context, transactionPage::class.java)
            context.startActivity(intent)
        }

        analysisButton.setOnClickListener {
            val intent = Intent(context, analysisPage::class.java)
            context.startActivity(intent)
        }

        categoriesButton.setOnClickListener {
            val intent = Intent(context, categories::class.java)
            context.startActivity(intent)
        }

        profileButton.setOnClickListener {
            val intent = Intent(context, profile::class.java)
            context.startActivity(intent)
        }
    }
}
