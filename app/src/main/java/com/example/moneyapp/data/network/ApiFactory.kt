package com.example.moneyapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class ApiFactory {
    object ApiFactory {
        private const val BASE_URL = "https://www.cbr-xml-daily.ru/"

        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

        val apiService = retrofit.create(ApiService::class.java)
    }
}