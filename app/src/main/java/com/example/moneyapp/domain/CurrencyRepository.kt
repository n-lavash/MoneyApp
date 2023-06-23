package com.example.moneyapp.domain

interface CurrencyRepository {
    suspend fun getListCurrency(date: Long): List<CurrencyInfo>?
    suspend fun loadData(date: Long)
}