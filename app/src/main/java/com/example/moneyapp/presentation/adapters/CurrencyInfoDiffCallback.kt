package com.example.moneyapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.moneyapp.domain.CurrencyInfo

object CurrencyInfoDiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {
    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}