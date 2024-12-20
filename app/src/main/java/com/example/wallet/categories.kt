package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.model.Categories
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat

class categories : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_categories)
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
        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)

        fun formatCurrency(amount: Long): String {
            val formatter = DecimalFormat("#,###")
            return "Rp ${formatter.format(amount)}"
        }

        val expenseTextView = findViewById<TextView>(R.id.tv_total_expense)
        val incomeTextView = findViewById<TextView>(R.id.tv_total_income)

        val jenisKategori = listOf("Entertainment", "Food", "Gift", "Groceries", "Medicine", "Rent", "Savings", "Transport")

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


        val tvaddtransaction = findViewById<View>(R.id.add_transaction)
        tvaddtransaction.setOnClickListener {
            val intent = Intent(this, addExpense::class.java)
            startActivity(intent)
        }
        val makanan = findViewById<LinearLayout>(R.id.llmakanan)
        val transport = findViewById<LinearLayout>(R.id.lltransport)
        val medicine = findViewById<LinearLayout>(R.id.llmedicine)
        val rent = findViewById<LinearLayout>(R.id.llrent)
        val groceries = findViewById<LinearLayout>(R.id.llgroceries)
        val gifts = findViewById<LinearLayout>(R.id.llgifts)
        val savings = findViewById<LinearLayout>(R.id.llsavings)
        val entertainment = findViewById<LinearLayout>(R.id.llentertaiment)

        makanan.setOnClickListener {
            openActivitySubCategories("Food", R.drawable.makanan, 50000)
        }

        transport.setOnClickListener {
            openActivitySubCategories("Transport", R.drawable.transport, 30000)
        }

        medicine.setOnClickListener {
            openActivitySubCategories("Medicine", R.drawable.medicine, 10000)
        }

        rent.setOnClickListener {
            openActivitySubCategories("Rent", R.drawable.rents, 800000)
        }

        groceries.setOnClickListener {
            openActivitySubCategories("Groceries", R.drawable.grocier, 150000)
        }

        gifts.setOnClickListener {
            openActivitySubCategories("Gift", R.drawable.gifts, 70000)
        }

        entertainment.setOnClickListener {
            openActivitySubCategories("Entertainment", R.drawable.entertaiment, 100000)
        }
        savings.setOnClickListener {
            openActivitySubCategories("Savings", R.drawable.savings, 100000)
        }

    }

    private fun openActivitySubCategories(title: String, imageResId: Int, expense: Int) {
        val intent = Intent(this, activity_sub_categories::class.java).apply {
            putExtra("TITLE", title)
            putExtra("IMAGE_RES_ID", imageResId)
            putExtra("EXPENSE", expense)
        }
        startActivity(intent)
    }

}
