package com.example.wallet

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


class AnalysisPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis_page)
        val tabDaily: TextView = findViewById(R.id.tab_daily)
        val tabWeekly: TextView = findViewById(R.id.tab_weekly)
        val tabMonthly: TextView = findViewById(R.id.tab_monthly)
        val incomeText: TextView = findViewById(R.id.income_text)
        val expenseText: TextView = findViewById(R.id.expense_text)
        val barChart: BarChart = findViewById(R.id.bar_chart)
        val lineChart: LineChart = findViewById(R.id.line_chart)
        val pieChart: PieChart = findViewById(R.id.pie_chart)

        updateData("Daily", incomeText, expenseText, barChart, lineChart, pieChart)

        val tabs = listOf(tabDaily, tabWeekly, tabMonthly)
        tabs.forEach { tab ->
            tab.setOnClickListener {
                tabs.forEach { it.isSelected = false }
                tab.isSelected = true
                when (tab.id) {
                    R.id.tab_daily -> updateData("Daily", incomeText, expenseText, barChart, lineChart, pieChart)
                    R.id.tab_weekly -> updateData("Weekly", incomeText, expenseText, barChart, lineChart, pieChart)
                    R.id.tab_monthly -> updateData("Monthly", incomeText, expenseText, barChart, lineChart, pieChart)
                }
            }
        }

        tabDaily.performClick()
    }

    private fun updateData(
        tab: String,
        incomeText: TextView,
        expenseText: TextView,
        barChart: BarChart,
        lineChart: LineChart,
        pieChart: PieChart
    ) {
        val income: String
        val expense: String
        val barData: List<BarEntry>
        val lineData: List<Entry>
        val pieData: List<PieEntry>
        val xAxisLabels: List<String> // This will hold the custom X-axis labels

        when (tab) {
            "Daily" -> {
                income = "Rp 9.000"
                expense = "Rp 1.000"
                barData = listOf(
                    BarEntry(1f, 2000f), // 00:00 - 04:00
                    BarEntry(2f, 2200f), // 04:00 - 08:00
                    BarEntry(3f, 2300f), // 08:00 - 12:00
                    BarEntry(4f, 2500f), // 12:00 - 16:00
                    BarEntry(5f, 2800f), // 16:00 - 20:00
                    BarEntry(6f, 3200f)  // 20:00 - 24:00
                )
                lineData = listOf(
                    Entry(1f, 2000f),
                    Entry(2f, 2200f),
                    Entry(3f, 2300f),
                    Entry(4f, 2500f),
                    Entry(5f, 2800f),
                    Entry(6f, 3200f)
                )
                pieData = listOf(
                    PieEntry(70f, "Income"),
                    PieEntry(30f, "Expense")
                )
                xAxisLabels = listOf("", "00:00", "04:00", "08:00", "12:00", "16:00", "20:00")
            }
            "Weekly" -> {
                income = "Rp 63.000"
                expense = "Rp 7.000"
                barData = listOf(
                    BarEntry(1f, 10000f), // Monday
                    BarEntry(2f, 12000f), // Tuesday
                    BarEntry(3f, 14000f), // Wednesday
                    BarEntry(4f, 15000f), // Thursday
                    BarEntry(5f, 18000f), // Friday
                    BarEntry(6f, 19000f), // Saturday
                    BarEntry(7f, 20000f)  // Sunday
                )
                lineData = listOf(
                    Entry(1f, 10000f),
                    Entry(2f, 12000f),
                    Entry(3f, 14000f),
                    Entry(4f, 15000f),
                    Entry(5f, 18000f),
                    Entry(6f, 19000f),
                    Entry(7f, 20000f)
                )
                pieData = listOf(
                    PieEntry(50f, "Income"),
                    PieEntry(50f, "Expense")
                )
                xAxisLabels = listOf("-", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" , "Sun")
            }
            "Monthly" -> {
                income = "Rp 270.000"
                expense = "Rp 30.000"
                barData = listOf(
                    BarEntry(1f, 45000f), // Month 1
                    BarEntry(2f, 50000f), // Month 2
                    BarEntry(3f, 55000f), // Month 3
                    BarEntry(4f, 60000f), // Month 4
                    BarEntry(5f, 70000f)  // Month 5
                )
                lineData = listOf(
                    Entry(1f, 45000f),
                    Entry(2f, 50000f),
                    Entry(3f, 55000f),
                    Entry(4f, 60000f),
                    Entry(5f, 70000f)
                )
                pieData = listOf(
                    PieEntry(90f, "Income"),
                    PieEntry(10f, "Expense")
                )
                xAxisLabels = listOf("", "Month 1", "Month 2", "Month 3", "Month 4", "Month 5")
            }
            else -> return
        }

        incomeText.text = "$income"
        expenseText.text = "$expense"

        val customTypeface = ResourcesCompat.getFont(this, R.font.montserrat_medium)

        // Bar chart setup
        val barDataSet = BarDataSet(barData, "Income")
        barDataSet.color = Color.BLACK
        val barDataObject = BarData(barDataSet)
//        barChart.xAxis.granularity = 1f
        barDataSet.valueTypeface = customTypeface
        barDataObject.barWidth = 0.5f
        barChart.data = barDataObject
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.textSize = 12f
        barChart.axisLeft.textSize = 12f
        barChart.invalidate()
        barDataSet.setDrawValues(false)
        barChart.xAxis.setDrawGridLines(false)
        barChart.axisRight.setDrawGridLines(false)
        barChart.axisLeft.setDrawGridLines(false)
        barChart.axisRight.isEnabled = false
        barChart.animateY(1500)

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

        // Line chart setup
        val lineDataSet = LineDataSet(lineData, "Income Trend")
        lineDataSet.color = Color.BLACK
        lineDataSet.valueTypeface = customTypeface
        lineDataSet.setCircleColor(Color.BLACK)
        lineChart.data = LineData(lineDataSet)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.granularity = 1f
        lineChart.xAxis.textSize = 12f
        lineChart.axisLeft.textSize = 12f
        lineChart.invalidate()
        lineDataSet.setDrawValues(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisRight.isEnabled = false
        lineChart.animateY(1500)

        // Set custom X-axis labels for line chart
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

        // Pie chart setup
        val pieDataSet = PieDataSet(pieData, "").apply {
            colors = listOf(
                ContextCompat.getColor(this@AnalysisPage, R.color.green),
                ContextCompat.getColor(this@AnalysisPage, R.color.red)
            )
            setSliceSpace(5f)
            valueTypeface = customTypeface
            valueTextSize = 12f
            setValueTextColors(listOf(Color.WHITE))
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "${value.toInt()}%"
                }
            }
        }

        val pieDataObject = PieData(pieDataSet)

        pieChart.apply {
            data = pieDataObject
            legend.isEnabled = true
            legend.textSize = 14f
            description.isEnabled = false
            centerText = "Budgeting"
            setCenterTextColor(Color.BLACK)
            setCenterTextSize(12f)
            isDrawHoleEnabled = true
            invalidate()
            setDrawEntryLabels(false)
            animateY(1500)
            animateX(1500)
        }
    }
}
