package com.example.wallet.model

data class Transaction(
    val amount: Int = 0,
    val category: String = "",
    val date: String = "",
    val expenseTitle: String = "",
    val type:String=""
)
