package com.example.moneyapp.data.repository

import android.util.Log
import com.example.moneyapp.data.database.CurrencyInfoDao
import com.example.moneyapp.data.mapper.CurrencyMapper
import com.example.moneyapp.data.network.ApiFactory
import com.example.moneyapp.domain.CurrencyInfo
import com.example.moneyapp.domain.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val mapper: CurrencyMapper,
    private val currencyInfoDao: CurrencyInfoDao
) : CurrencyRepository {

    private val apiService = ApiFactory.ApiFactory.apiService

    override suspend fun getListCurrency(date: Long): List<CurrencyInfo>? {
        val list = currencyInfoDao.getListCurrencyByDate(date)?.currencyInfoList
        return list?.map { mapper.mapDbModelToEntity(it) }
    }

    override suspend fun loadData(date: Long) {
        try {
            val listInfo = apiService.getCurrencyInfoListByDate()

            listInfo.currencyInfoList?.let {

                val listDto = it.values.toList()
                val listDbModel = mapper.mapListDtoToListDbModel(listDto)

                currencyInfoDao.insertListCurrency(
                    mapper.mapListDbModelToByDateDbModel(
                        listDbModel,
                        date
                    )
                )
            }
        } catch (e: Exception) {
            Log.d("CurrencyRepositoryImpl", "error load data from network", e)
        }
    }
}