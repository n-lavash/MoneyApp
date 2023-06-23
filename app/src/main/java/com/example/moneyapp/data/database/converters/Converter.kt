package com.example.moneyapp.data.database.converters

import com.example.moneyapp.data.database.CurrencyInfoDbModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.room.TypeConverter


class Converter {

    @TypeConverter
    fun listCurrencyInfoToString(listCurrencyInfoDbModel: List<CurrencyInfoDbModel>) =
        Gson().toJson(listCurrencyInfoDbModel)

    @TypeConverter
    fun stringToListCurrencyInfo(string: String): List<CurrencyInfoDbModel> {
        val gson = Gson()
        val listType = object : TypeToken<List<CurrencyInfoDbModel>>() {}.type
        return gson.fromJson(string, listType)
    }

    companion object {
        private const val ID = "id"
        private const val CHAR_CODE = "charCode"
        private const val NOMINAL = "nominal"
        private const val NAME = "name"
        private const val VALUE = "value"
    }
}