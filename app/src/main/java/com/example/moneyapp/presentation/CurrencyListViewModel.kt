package com.example.moneyapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneyapp.domain.CurrencyInfo
import com.example.moneyapp.domain.GetListCurrencyUseCase
import com.example.moneyapp.domain.LoadDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CurrencyListViewModel @Inject constructor(
    private val getListCurrencyUseCase: GetListCurrencyUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val _currencyInfoLiveData = MutableLiveData<List<CurrencyInfo>>()
    val currencyInfoLiveData: LiveData<List<CurrencyInfo>>
        get() = _currencyInfoLiveData

    private val _isLoadData = MutableLiveData<Boolean>()
    val isLoadData: LiveData<Boolean>
        get() = _isLoadData

    private val _errorLoadData = MutableLiveData<Boolean>()
    val errorLoadData: LiveData<Boolean>
        get() = _errorLoadData

    fun getListCurrency(date: Long) {
        val dateParse = date.convertDateToString()

        viewModelScope.launch {
            val listInfo = getListCurrencyUseCase(dateParse)
            if (listInfo.isNullOrEmpty()) {
                _isLoadData.value = false
                loadData(dateParse)
            } else {
                _currencyInfoLiveData.value = listInfo
                _isLoadData.value = true
                _errorLoadData.value = false
            }
        }
    }

    private suspend fun loadData(date: Long) {
        loadDataUseCase(date)
        val listInfo = getListCurrencyUseCase(date)

        if (listInfo.isNullOrEmpty())
            _errorLoadData.value = true
        else {
            _currencyInfoLiveData.value = listInfo
            _isLoadData.value = true
            _errorLoadData.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    private fun Long.convertDateToString(): Long {
        val pattern = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(pattern)
        return dateFormat.format(Date(this)).convertDateToLong()
    }

    private fun String.convertDateToLong(): Long {
        val pattern = "dd/MM/yyyy"
        val dateFormat = SimpleDateFormat(pattern)
        val date = dateFormat.parse(this)
        return date.time
    }
}