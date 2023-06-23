package com.example.moneyapp.domain

import javax.inject.Inject

class GetListCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {
    suspend operator fun invoke(date: Long): List<CurrencyInfo>? {
        return repository.getListCurrency(date)
    }
}
