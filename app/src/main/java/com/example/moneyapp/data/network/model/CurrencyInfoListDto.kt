package com.example.moneyapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyInfoListDto(
    @SerializedName("Date")
    @Expose
    val date: String,

    @SerializedName("Valute")
    @Expose
    val currencyInfoList: Map<String, CurrencyInfoDto>? = null
)