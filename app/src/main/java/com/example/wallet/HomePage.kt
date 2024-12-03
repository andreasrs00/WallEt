package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.wallet.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat


class HomePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        findViewById<View>(R.id.img_profile).setOnClickListener {
            val intent = Intent(this, editprofilePage::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            findViewById<RelativeLayout>(R.id.containernavbar).setPadding(
                0, 0, 0, systemBars.bottom
            )
            insets
        }
        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this,navbar)

        val jenisKategori = listOf("Entertainment", "Food", "Gift", "Groceries", "Medicine", "Rent", "Savings", "Transport")

        fun formatCurrency(amount: Long): String {
            val formatter = DecimalFormat("#,###")
            return "Rp ${formatter.format(amount)}"
        }

        val expenseTextView = findViewById<TextView>(R.id.tv_total_expense)
        val incomeTextView = findViewById<TextView>(R.id.tv_total_income)


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

        val barChart = findViewById<BarChart>(R.id.barChart)

        val entries = arrayListOf(
            BarEntry(0f, 1000000f),
            BarEntry(1f, 1500000f),
            BarEntry(2f, 3000000f),
            BarEntry(3f, 1200000f),
            BarEntry(4f, 900000f),
            BarEntry(5f, 1350000f)
        )

        val dataSet = BarDataSet(entries, "Total Balance")
        dataSet.color = resources.getColor(android.R.color.black, theme)
        dataSet.setDrawValues(false)

        val data = BarData(dataSet)
        barChart.data = data

        val months = arrayOf("Jul", "Aug", "Oct", "Dec", "Jan", "Jun")
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.textSize = 12f

        barChart.axisLeft.textSize = 12f
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisLeft.axisMinimum = 0f

        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.animateY(1000)
    }

}
