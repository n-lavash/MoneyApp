package com.example.moneyapp.presentation

import android.app.Application
import com.example.moneyapp.di.DaggerApplicationComponent

class CurrencyApp : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}