package com.example.moneyapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrencyInfo(
    val id: String,
    val charCode: String,
    val nominal: Int,
    val name: String,
    val value: Double
) : Parcelable