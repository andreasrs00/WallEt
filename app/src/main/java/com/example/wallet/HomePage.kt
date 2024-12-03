package com.example.wallet


import java.util.Calendar
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
import android.widget.LinearLayout
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

        val transaction: LinearLayout = findViewById(R.id.transaction)

        transaction.setOnClickListener {
            val intent = Intent(this, transactionPage::class.java)
            startActivity(intent)
        }

        val financial: LinearLayout = findViewById(R.id.ll_financialreport)

        financial.setOnClickListener{
            val intent = Intent(this, AnalysisPage::class.java)
            startActivity(intent)
        }

        val savingsGrowth: LinearLayout = findViewById(R.id.ll_saving_growth)
        savingsGrowth.setOnClickListener {
            val intent = Intent(this, AnalysisPage::class.java)
            intent.putExtra("scroll_to", "line_chart_title") // Mengirim ID target
            startActivity(intent)
        }

        val spendingChart: LinearLayout = findViewById(R.id.ll_spending_chart)
        spendingChart.setOnClickListener {
            val intent = Intent(this, AnalysisPage::class.java)
            intent.putExtra("scroll_to", "pie_chart_title") // Mengirim ID target
            startActivity(intent)
        }

        val Investment: LinearLayout = findViewById(R.id.ll_investment)

        Investment.setOnClickListener{
            val intent = Intent(this, AnalysisPage::class.java)
            startActivity(intent)
        }

        val expenseTrend: LinearLayout = findViewById(R.id.ll_expend_growth)

        expenseTrend.setOnClickListener{
            val intent = Intent(this, AnalysisPage::class.java)
            intent.putExtra("scroll_to", "line_chart_title2") // Mengirim ID target
            startActivity(intent)
        }

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
        val months = listOf("Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

        fun updateBarChart(monthlyExpenses: Map<Int, Float>, lastSixMonths: List<Int>, months: List<String>, barChart: BarChart) {

            val entries = lastSixMonths.map { month ->
                BarEntry(month.toFloat(), monthlyExpenses[month] ?: 0f)
            }

            val dataSet = BarDataSet(entries, "Monthly Expenses")
            dataSet.color = resources.getColor(android.R.color.black, theme)
            dataSet.setDrawValues(false)
            dataSet.valueTextSize = 12f


            val barData = BarData(dataSet)
            barChart.data = barData

            val xAxis = barChart.xAxis
            xAxis.valueFormatter = IndexAxisValueFormatter(months)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
//            xAxis.granularity = 1f
            xAxis.textSize = 12f

            barChart.axisLeft.textSize = 12f
            barChart.axisLeft.setDrawGridLines(false)
            barChart.axisLeft.axisMinimum = 0f

            barChart.axisRight.isEnabled = false
            barChart.description.isEnabled = false
            barChart.legend.isEnabled = false
            barChart.axisLeft.setDrawGridLines(false)
            barChart.animateY(1000)
            barChart.invalidate()
        }

        if (userID != null) {
            val monthlyExpenses = mutableMapOf<Int, Float>() // Menyimpan total pengeluaran per bulan
            val currentCalendar = Calendar.getInstance()
            val currentMonth = currentCalendar.get(Calendar.MONTH) // Ambil bulan saat ini (0-11)

            val lastSixMonths = (currentMonth - 5..currentMonth).map { (it + 12) % 12 } // Menjaga bulan tetap dalam rentang 0-11

            for (kategori in jenisKategori) {
                db.collection("Transaction")
                    .document(userID)
                    .collection("categories")
                    .document(kategori)
                    .collection("expense")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        if (snapshot != null && !snapshot.isEmpty) {
                            for (document in snapshot.documents) {
                                val amount = document.getLong("Amount") ?: 0L
                                val date = document.getString("Date") ?: continue
                                val monthIndex = date.split("-")[1].toIntOrNull()?.minus(1) ?: continue // Ambil bulan dari "YYYY-MM-DD" (0-11)

                                if (monthIndex in lastSixMonths) {
                                    monthlyExpenses[monthIndex] = (monthlyExpenses[monthIndex] ?: 0f) + amount.toFloat()
                                }
                            }

                            // Setelah data diambil, buat BarChart
                            updateBarChart(monthlyExpenses, lastSixMonths, months, barChart)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("FirestoreError", "Error getting expense data for $kategori: ", exception)
                    }
            }
        } else {
            Log.e("FirestoreError", "User ID is null.")
        }

    }

}
