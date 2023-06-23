package com.example.moneyapp.di

import android.app.Application
import com.example.moneyapp.presentation.CurrencyConverterActivity
import com.example.moneyapp.presentation.CurrencySelectionActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: CurrencySelectionActivity)

    fun inject(activity: CurrencyConverterActivity)

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}