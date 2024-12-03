package com.example.wallet

import CategoryAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.model.Categories
import com.example.wallet.model.Transaction
import com.google.firebase.auth.FirebaseAuth
import java.text.DecimalFormat
import com.google.firebase.firestore.FirebaseFirestore

class activity_sub_categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sub_categories)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            findViewById<RelativeLayout>(R.id.containernavbar).setPadding(
                0, 0, 0, systemBars.bottom
            )
            insets
        }
        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this,navbar)

        val backarrorw: ImageView = findViewById(R.id.backArrow)

        backarrorw.setOnClickListener {
            val intent = Intent(this, categories::class.java)
            startActivity(intent)
        }

        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        val userID = auth.currentUser?.uid

        val title = intent.getStringExtra("TITLE")

        val titleTextView = findViewById<TextView>(R.id.judul)
        val expenseTextView = findViewById<TextView>(R.id.tv_expense)
        val incomeTextView = findViewById<TextView>(R.id.tv_income)

        titleTextView.text = title

        val recyclerView = findViewById<RecyclerView>(R.id.recyclecategory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val imageResId = intent.getIntExtra("IMAGE_RES_ID", 0)

        fun fetchTransactionDataSada(collectionName: String) {
            db.collection("Transaction").document(userID.toString())
                .collection("categories").document(title.toString())
                .collection(collectionName).limit(10).get()
                .addOnSuccessListener { snapshot ->
                    val categoryList = mutableListOf<Categories>()
                    for (document in snapshot.documents) {
                        val uang = document.getLong("Amount") ?: 0L
                        val waktu = document.getString("Date") ?: ""
                        val kegiatan = document.getString("Expense Title") ?: ""

                        val formattedUang = "Rp. ${DecimalFormat("#,###").format(uang)}"
                        val category = Categories(imageResId, formattedUang, waktu, kegiatan)

                        if (category.imageResId == imageResId) {
                            categoryList.add(category)
                        }
                    }

                    // Set the filtered category list to the adapter
                    recyclerView.adapter = CategoryAdapter(categoryList)
                }
        }
        if (title == "Entertainment" || title == "Food" || title == "Gift" || title == "Groceries" || title == "Medicine" || title == "Rent" || title == "Transport") {
            fetchTransactionDataSada("expense");
        } else if (title == "Savings") {
            fetchTransactionDataSada("income");
        }

        if (userID == null) return

        fun fetchTransactionData(collectionName: String, textView: TextView) {
            db.collection("Transaction").document(userID)
                .collection("categories").document(title.toString())
                .collection(collectionName).limit(10).get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        var totalAmount: Long = 0
                        for (document in snapshot.documents) {
                            val amount = document.getLong("Amount") ?: 0L
                            totalAmount += amount
                        }

                        val formattedUang2 = "Rp. ${DecimalFormat("#,###").format(totalAmount)}"
                        textView.text = "$formattedUang2"
                    }
                }
        }
        if (title == "Entertainment" || title == "Food" || title == "Gift" || title == "Groceries" || title == "Medicine" || title == "Rent" || title == "Transport") {
            fetchTransactionData("expense", expenseTextView);
        } else if (title == "Savings") {
            fetchTransactionData("income", incomeTextView);
        }
    }
}