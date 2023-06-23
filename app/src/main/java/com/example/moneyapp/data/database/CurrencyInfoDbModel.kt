package com.example.moneyapp.data.database

data class CurrencyInfoDbModel(
    val id: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double
)