package com.example.moneyapp.data.mapper

import com.example.moneyapp.data.database.CurrencyInfoByDateDbModel
import com.example.moneyapp.data.database.CurrencyInfoDbModel
import com.example.moneyapp.data.network.model.CurrencyInfoDto
import com.example.moneyapp.domain.CurrencyInfo
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {
    fun mapDtoToDbModel(currencyInfoDto: CurrencyInfoDto) = CurrencyInfoDbModel(
        id = currencyInfoDto.id,
        charCode = currencyInfoDto.charCode,
        nominal = currencyInfoDto.nominal,
        name = currencyInfoDto.name,
        value = currencyInfoDto.value
    )

    fun mapDbModelToEntity(currencyInfoDbModel: CurrencyInfoDbModel) = CurrencyInfo(
        id = currencyInfoDbModel.id,
        charCode = currencyInfoDbModel.charCode,
        nominal = currencyInfoDbModel.nominal,
        name = currencyInfoDbModel.name,
        value = currencyInfoDbModel.value
    )

    fun mapListDtoToListDbModel(listCurrencyInfoDto: List<CurrencyInfoDto>) =
        listCurrencyInfoDto.map { map ->
            mapDtoToDbModel(map)
        }

    fun mapListDbModelToByDateDbModel(
        listCurrencyInfoDbModel: List<CurrencyInfoDbModel>,
        date: Long
    ) =
        CurrencyInfoByDateDbModel(
            date = date,
            currencyInfoList = listCurrencyInfoDbModel
        )

    fun mapByDateDbModelToListDbModel(
        currencyInfoByDateDbModel: CurrencyInfoByDateDbModel
    ): List<CurrencyInfoDbModel> = currencyInfoByDateDbModel.currencyInfoList
}