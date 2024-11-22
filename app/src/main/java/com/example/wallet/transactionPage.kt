package com.example.wallet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.adapter.MonthAdapter
import com.example.wallet.model.CardItem
import com.example.wallet.model.Month

class transactionPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_page)

        val januaryItems = listOf(
            CardItem("Salary", "Monthly", "+ 400.000", R.drawable.salary_icon),
            CardItem("Groceries", "Pantry", "- 200.000", R.drawable.groceries_icon),
            CardItem("Rent", "Rent", "- 9.243.674", R.drawable.rent_icon)
        )

        val februaryItems = listOf(
            CardItem("Transport", "Fuel", "- 150.000", R.drawable.dollar_icon),
            CardItem("Transport", "Electric", "- 200.000", R.drawable.dollar_icon),
            CardItem("Transport", "Electric", "- 200.000", R.drawable.dollar_icon)
        )

        val marchItems = listOf(
            CardItem("Salary", "Monthly", "+ 400.000", R.drawable.salary_icon),
            CardItem("Salary", "Clothes", "- 200.000", R.drawable.salary_icon),
            CardItem("Salary", "Monthly", "+ 120.000", R.drawable.salary_icon),
            CardItem("Salary", "Monthly", "+ 200.000", R.drawable.salary_icon)
        )

        val monthList = listOf(
            Month("January", januaryItems),
            Month("February", februaryItems),
            Month("March", marchItems)
        )

        val recyclerView: RecyclerView = findViewById(R.id.month_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MonthAdapter(monthList)

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)
    }
}
