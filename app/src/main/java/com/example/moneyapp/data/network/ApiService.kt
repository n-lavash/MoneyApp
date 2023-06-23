package com.example.moneyapp.data.network

import com.example.moneyapp.data.network.model.CurrencyInfoListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(END_POINT)
    suspend fun getCurrencyInfoListByDate(
        @Query(DATE_REQ) dateReq: String = ""
    ): CurrencyInfoListDto

    companion object {
        private const val END_POINT: String = "daily_json.js"
        private const val DATE_REQ: String = "date_req"
    }
}