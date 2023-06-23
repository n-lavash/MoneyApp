package com.example.moneyapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ActivityCurrencyConverterBinding
import com.example.moneyapp.domain.CurrencyInfo
import javax.inject.Inject

class CurrencyConverterActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CurrencyApp).component
    }

    private lateinit var currencyInfo: CurrencyInfo

    private val binding by lazy {
        ActivityCurrencyConverterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        parseParams()

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCustomActionBar()

        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyListViewModel::class.java]

        setViews()
        startConvert()
    }

    private fun parseParams() {
        if (!intent.hasExtra(EXTRA_OBJECT))
            finish()

        intent.getParcelableExtra<CurrencyInfo>(EXTRA_OBJECT)?.let {
            currencyInfo = it
        }
    }

    private fun setViews() {
        with(binding) {
            textViewCurrencyFullName.text = currencyInfo.name
            textViewCurrencyValue.text = String.format(
                resources.getString(R.string.tv_currency_item_value),
                currencyInfo.value
            )

            textInputLayoutCountOfCurrency.hint = currencyInfo.charCode
        }
    }

    private fun setCustomActionBar() {
        supportActionBar?.let {
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setCustomView(R.layout.custom_action_bar)
            it.customView.findViewById<TextView>(R.id.textViewTitleActionBar).text =
                currencyInfo.charCode

            it.customView.findViewById<ImageView>(R.id.imageViewBack).isVisible = true
            it.customView.findViewById<ImageView>(R.id.imageViewBack).setOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun startConvert() {

        var isChangeText = false

        binding.textInputEditCountOfCurrency.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (!s.isNullOrEmpty() && s == "0")
                    binding.textInputEditCountOfCurrency.setText("")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !isChangeText) {
                    isChangeText = true
                    val valueFrom = currencyInfo.value
                    val convertValue = convertToRuble(valueFrom, s.toString().toDouble())
                    binding.textInputEditCountOfRuble.setText(convertValue.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isChangeText = false
            }
        })

        binding.textInputEditCountOfRuble.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (!s.isNullOrEmpty() && s == "0")
                    binding.textInputEditCountOfCurrency.setText("")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty() && !isChangeText) {
                    isChangeText = true
                    val valueFrom = currencyInfo.value
                    val convertValue = convertFromRuble(valueFrom, s.toString().toDouble())
                    binding.textInputEditCountOfCurrency.setText(convertValue.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
                isChangeText = false
            }
        })
    }

    private fun convertToRuble(valueFrom: Double, nominal: Double) = valueFrom * nominal

    private fun convertFromRuble(valueFrom: Double, nominal: Double) = nominal / valueFrom

    companion object {
        private const val EXTRA_OBJECT = "object"

        fun newIntent(context: Context, currencyInfo: CurrencyInfo): Intent {
            val intent = Intent(context, CurrencyConverterActivity::class.java)
            intent.putExtra(EXTRA_OBJECT, currencyInfo)
            return intent
        }
    }
}