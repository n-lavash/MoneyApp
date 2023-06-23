package com.example.moneyapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM currency_info_by_date WHERE date == :dateUpdate LIMIT 1")
    suspend fun getListCurrencyByDate(dateUpdate: Long): CurrencyInfoByDateDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListCurrency(list: CurrencyInfoByDateDbModel)
}