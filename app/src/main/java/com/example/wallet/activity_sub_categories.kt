package com.example.wallet

import CategoryAdapter
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.model.Categories

class activity_sub_categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sub_categories)

        val title = intent.getStringExtra("TITLE")
        val expense = intent.getIntExtra("EXPENSE", 0)

        val titleTextView = findViewById<TextView>(R.id.judul)
        val expenseTextView = findViewById<TextView>(R.id.tv_expense)

        titleTextView.text = title
        expenseTextView.text = "Rp. $expense"


        val recyclerView = findViewById<RecyclerView>(R.id.recyclecategory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Filter data berdasarkan imageResId yang diterima
        val imageResId = intent.getIntExtra("IMAGE_RES_ID", 0)
        val filteredList = CategoryData.categoryList.filter { it.imageResId == imageResId }

        // Set adapter
        recyclerView.adapter = CategoryAdapter(filteredList)

    }


}