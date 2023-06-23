package com.example.moneyapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moneyapp.data.database.converters.Converter

@Entity(tableName = "currency_info_by_date")
@TypeConverters(value = [Converter::class])
data class CurrencyInfoByDateDbModel(
    @PrimaryKey()
    val date: Long,
    val currencyInfoList: List<CurrencyInfoDbModel>
)