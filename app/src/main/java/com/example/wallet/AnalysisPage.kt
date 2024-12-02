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
        val lineChart2: LineChart = findViewById(R.id.line_chart2)

        updateData("Daily", incomeText, expenseText, barChart, lineChart, pieChart, lineChart2)

        val tabs = listOf(tabDaily, tabWeekly, tabMonthly)
        tabs.forEach { tab ->
            tab.setOnClickListener {
                tabs.forEach { it.isSelected = false }
                tab.isSelected = true
                when (tab.id) {
                    R.id.tab_daily -> updateData("Daily", incomeText, expenseText, barChart, lineChart, pieChart, lineChart2)
                    R.id.tab_weekly -> updateData("Weekly", incomeText, expenseText, barChart, lineChart, pieChart, lineChart2)
                    R.id.tab_monthly -> updateData("Monthly", incomeText, expenseText, barChart, lineChart, pieChart, lineChart2)
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
        pieChart: PieChart,
        lineChart2: LineChart
    ) {
        val income: String
        val expense: String
        val barData: List<BarEntry>
        val lineData: List<Entry>
        val pieData: List<PieEntry>
        val xAxisLabels: List<String>
        val lineData2: List<Entry>

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
                    PieEntry(30f, "Food"),
                    PieEntry(10f, "Transport"),
                    PieEntry(40f, "Entertaiment"),
                    PieEntry(20f, "Savings")
                )
                xAxisLabels = listOf("", "00:00", "04:00", "08:00", "12:00", "16:00", "20:00")
                lineData2 = listOf(
                    Entry(1f, 42700f),
                    Entry(2f, 56000f),
                    Entry(3f, 92000f),
                    Entry(4f, 38000f),
                    Entry(5f, 50000f),
                    Entry(6f, 82500f)
                )
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
                    PieEntry(10f, "Food"),
                    PieEntry(40f, "Transport"),
                    PieEntry(20f, "Entertaiment"),
                    PieEntry(30f, "Savings")
                )
                xAxisLabels = listOf("-", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" , "Sun")
                lineData2 = listOf(
                    Entry(1f, 47500f),
                    Entry(2f, 60200f),
                    Entry(3f, 120000f),
                    Entry(4f, 55000f),
                    Entry(5f, 62000f),
                    Entry(6f, 80000f),
                    Entry(7f, 92000f)
                )
            }
            "Monthly" -> {
                income = "Rp 270.000"
                expense = "Rp 30.000"
                barData = listOf(
                    BarEntry(1f, 45000f), // Month 1
                    BarEntry(2f, 50000f), // Month 2
                    BarEntry(3f, 55000f), // Month 3
                    BarEntry(4f, 60000f) // Month 4
                )
                lineData = listOf(
                    Entry(1f, 45000f),
                    Entry(2f, 50000f),
                    Entry(3f, 55000f),
                    Entry(4f, 60000f)
                )
                pieData = listOf(
                    PieEntry(50f, "Food"),
                    PieEntry(15f, "Transport"),
                    PieEntry(20f, "Entertaiment"),
                    PieEntry(15f, "Savings")
                )
                xAxisLabels = listOf("-", "Week 1", "Week 2", "Week 3", "Week 4", "Week 5")
                lineData2 = listOf(
                    Entry(1f, 24000f),
                    Entry(2f, 52000f),
                    Entry(3f, 32000f),
                    Entry(4f, 72000f)
                )
            }
            else -> return
        }

        incomeText.text = "$income"
        expenseText.text = "$expense"

        val customTypeface = ResourcesCompat.getFont(this, R.font.montserrat_medium)

        val barDataSet = BarDataSet(barData, "Income")
        barDataSet.color = Color.BLACK
        val barDataObject = BarData(barDataSet)
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

        val lineDataSet = LineDataSet(lineData, "Savings Trend")
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

        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)

        val pieDataSet = PieDataSet(pieData, "").apply {
            colors = listOf(
                ContextCompat.getColor(this@AnalysisPage, R.color.blue),
                ContextCompat.getColor(this@AnalysisPage, R.color.orange),
                ContextCompat.getColor(this@AnalysisPage, R.color.pink),
                ContextCompat.getColor(this@AnalysisPage, R.color.green)
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
            setCenterTextColor(Color.BLACK)
            setCenterTextSize(12f)
            isDrawHoleEnabled = true
            invalidate()
            setDrawEntryLabels(false)
            animateY(1500)
            animateX(1500)
        }

        val lineDataSet2 = LineDataSet(lineData2, "Expense Trend")
        lineDataSet2.color = Color.BLACK
        lineDataSet2.valueTypeface = customTypeface
        lineDataSet2.setCircleColor(Color.BLACK)
        lineChart2.data = LineData(lineDataSet2)
        lineChart2.description.isEnabled = false
        lineChart2.legend.isEnabled = false
        lineChart2.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart2.xAxis.granularity = 1f
        lineChart2.xAxis.textSize = 12f
        lineChart2.axisLeft.textSize = 12f
        lineChart2.invalidate()
        lineDataSet2.setDrawValues(false)
        lineChart2.xAxis.setDrawGridLines(false)
        lineChart2.axisRight.setDrawGridLines(false)
        lineChart2.axisLeft.setDrawGridLines(false)
        lineChart2.axisRight.isEnabled = false
        lineChart2.animateY(1500)

        lineChart2.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
    }
}
