package com.example.wallet

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class analysisPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analysis_page)

        // Handle insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Reference views
        val tabDaily: TextView = findViewById(R.id.tab_daily)
        val tabWeekly: TextView = findViewById(R.id.tab_weekly)
        val tabMonthly: TextView = findViewById(R.id.tab_monthly)
        val chartView: ImageView = findViewById(R.id.chart_view)
        val incomeText: TextView = findViewById(R.id.income_text)
        val expenseText: TextView = findViewById(R.id.expense_text)

        // List of all tabs
        val tabs = listOf(tabDaily, tabWeekly, tabMonthly)

        // Function to update data and handle UI changes
        fun updateData(tab: String) {
            when (tab) {
                "Daily" -> {
                    chartView.setImageResource(R.drawable.grafik_day)
                    incomeText.text = "Rp 9.000"
                    expenseText.text = "Rp 1.000"
                }
                "Weekly" -> {
                    chartView.setImageResource(R.drawable.grafik_weekly)
                    incomeText.text = "Rp 63.000"
                    expenseText.text = "Rp 7.000"
                }
                "Monthly" -> {
                    chartView.setImageResource(R.drawable.grafik_montly)
                    incomeText.text = "Rp 270.000"
                    expenseText.text = "Rp 30.000"
                }
            }
        }

        // Set click listeners for all tabs
        tabs.forEach { tab ->
            tab.setOnClickListener {
                // Reset selected state for all tabs
                tabs.forEach { it.isSelected = false }

                // Mark the clicked tab as selected
                tab.isSelected = true

                // Update data based on the selected tab
                when (tab.id) {
                    R.id.tab_daily -> updateData("Daily")
                    R.id.tab_weekly -> updateData("Weekly")
                    R.id.tab_monthly -> updateData("Monthly")
                }
            }
        }

        // Default tab selection (e.g., Daily tab selected on launch)
        tabDaily.performClick()

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)
    }
}
