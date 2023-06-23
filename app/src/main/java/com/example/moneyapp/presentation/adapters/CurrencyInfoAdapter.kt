package com.example.moneyapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ItemCurrencyBinding
import com.example.moneyapp.domain.CurrencyInfo
import java.text.DecimalFormat

class CurrencyInfoAdapter(
    val context: Context
) : ListAdapter<CurrencyInfo, CurrencyInfoViewHolder>(CurrencyInfoDiffCallback) {

    var onCurrencyClickListener: OnCurrencyClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyInfoViewHolder {
        val binding = ItemCurrencyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CurrencyInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyInfoViewHolder, position: Int) {
        val currencyInfo = getItem(position)

        with(holder.binding) {
            textViewCurrencyTitle.text = currencyInfo.charCode

            val valueTemplate = context.resources.getString(R.string.tv_currency_item_value)
            textViewCurrencyValue.text = String.format(valueTemplate, currencyInfo.value.format())

            root.setOnClickListener {
                onCurrencyClickListener?.onCurrencyClick(currencyInfo)
            }
        }
    }

    private fun Double.format(): String {
        val pattern = if (this > 100)
            "#.#"
        else
            "#.##"
        val decimalFormat = DecimalFormat(pattern)
        return decimalFormat.format(this)
    }



    interface OnCurrencyClickListener {
        fun onCurrencyClick(currencyInfo: CurrencyInfo)
    }
}