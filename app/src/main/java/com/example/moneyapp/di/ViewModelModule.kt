package com.example.moneyapp.di

import androidx.lifecycle.ViewModel
import com.example.moneyapp.presentation.CurrencyListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CurrencyListViewModel::class)
    fun bindCurrencyViewModel(viewModel: CurrencyListViewModel): ViewModel
}