package com.example.moneyapp.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(date: Long) = repository.loadData(date)
}