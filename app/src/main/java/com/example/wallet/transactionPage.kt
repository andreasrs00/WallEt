package com.example.wallet

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.adapter.MonthAdapter
import com.example.wallet.model.CardItem
import com.example.wallet.model.Month
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.util.Locale

class transactionPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_page)

        val expenseTextView = findViewById<TextView>(R.id.tv_total_expense)
        val incomeTextView = findViewById<TextView>(R.id.tv_total_income)

        val jenisKategori = listOf("Entertainment", "Food", "Gift", "Groceries", "Medicine", "Rent", "Savings", "Transport")

        fun formatCurrency(amount: Long): String {
            val formatter = DecimalFormat("#,###")
            return "Rp ${formatter.format(amount)}"
        }


        val db = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val userID = auth.currentUser?.uid

        var income: Long = 0
        var expense: Long = 0

        if (userID != null) {
            for (kategori in jenisKategori) {
                val documentRef = db.collection("Transaction")
                    .document(userID)
                    .collection("categories")
                    .document(kategori)

                listOf("expense", "income").forEach { type ->
                    documentRef.collection(type)
                        .get()
                        .addOnSuccessListener { snapshot ->
                            if (snapshot != null && !snapshot.isEmpty) {
                                for (document in snapshot.documents) {
                                    val uang = document.getLong("Amount") ?: 0L

                                    if (type == "income") {
                                        income += uang
                                    } else if (type == "expense") {
                                        expense += uang
                                    }
                                }

                                expenseTextView.text = formatCurrency(expense)
                                incomeTextView.text = formatCurrency(income)
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.e("FirestoreError", "Error getting $type data for $kategori: ", exception)
                        }
                }
            }
        } else {
            Log.e("FirestoreError", "User ID is null.")
        }

        val months = listOf(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        )

        val monthList = mutableListOf<Month>()

        fun updateRecyclerView(monthList: List<Month>) {
            val recyclerView: RecyclerView = findViewById(R.id.month_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = MonthAdapter(monthList)
        }


        fun getDrawableId(category: String): Int {
            val iconName = "${category.toLowerCase(Locale.ROOT)}_icon"
            return resources.getIdentifier(iconName, "drawable", packageName)
        }

        if (userID != null) {
            for ((index, month) in months.withIndex()) {
                val monthItems = mutableListOf<CardItem>()

                for (category in jenisKategori) {
                    listOf("income", "expense").forEach { type ->
                        db.collection("Transaction")
                            .document(userID)
                            .collection("categories")
                            .document(category)
                            .collection(type)
                            .get()
                            .addOnSuccessListener { snapshot ->
                                if (snapshot != null && !snapshot.isEmpty) {
                                    val items = snapshot.documents.mapNotNull { document ->
                                        val amount = document.getLong("Amount") ?: return@mapNotNull null
                                        val dateString = document.getString("Date") ?: return@mapNotNull null
                                        val expenseTitle = document.getString("Expense Title") ?: return@mapNotNull null

                                        // Memeriksa apakah tanggal sesuai dengan bulan yang sedang diiterasi
                                        val dateMonth = dateString.split("-")[1].toIntOrNull() // Ambil bulan dari "YYYY-MM-DD"
                                        if (dateMonth == index + 1) { // Cocokkan bulan (index + 1 karena Januari = 1)
                                            CardItem(
                                                name = category, // Menggunakan nama kategori
                                                type = expenseTitle, // Expense Title sebagai deskripsi
                                                amount = formatCurrency(amount),
                                                icon = getDrawableId(category.toLowerCase())
                                            )
                                        } else {
                                            null
                                        }
                                    }
                                    monthItems.addAll(items)
                                    if (monthItems.isNotEmpty()) {
                                        monthList.add(Month(month, monthItems))
                                        updateRecyclerView(monthList)
                                    }
                                }
                            }
                            .addOnFailureListener { exception ->
                                Log.e("FirestoreError", "Error getting $type data for $category: ", exception)
                            }
                    }
                }
            }
        }


        val recyclerView: RecyclerView = findViewById(R.id.month_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MonthAdapter(monthList)

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)
    }
}
