package com.example.moneyapp.di

import android.app.Application
import com.example.moneyapp.data.database.AppDatabase
import com.example.moneyapp.data.database.CurrencyInfoDao
import com.example.moneyapp.data.repository.CurrencyRepositoryImpl
import com.example.moneyapp.domain.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCurrencyRepository(repositoryImpl: CurrencyRepositoryImpl): CurrencyRepository

    companion object {
        @Provides
        fun provideCurrencyInfoDao(application: Application): CurrencyInfoDao {
            return AppDatabase.getInstance(application).currencyInfoDao()
        }
    }
}