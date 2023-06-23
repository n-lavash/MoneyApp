package com.example.moneyapp.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moneyapp.R
import com.example.moneyapp.databinding.ActivityCurrencySelectionBinding
import com.example.moneyapp.domain.CurrencyInfo
import com.example.moneyapp.presentation.adapters.CurrencyInfoAdapter
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CurrencySelectionActivity : AppCompatActivity() {

    private lateinit var viewModel: CurrencyListViewModel
    private lateinit var adapter: CurrencyInfoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as CurrencyApp).component
    }

    private val calendar by lazy {
        Calendar.getInstance()
    }

    private val dateListener by lazy {
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDate()
        }
    }

    private val binding by lazy {
        ActivityCurrencySelectionBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setCustomActionBar()

        setRecyclerView()

        binding.textInputEditDate.setOnClickListener {
            DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog,
                dateListener,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        adapter.onCurrencyClickListener = object : CurrencyInfoAdapter.OnCurrencyClickListener {
            override fun onCurrencyClick(currencyInfo: CurrencyInfo) {
                launchConverterActivity(currencyInfo)
            }
        }

        viewModel = ViewModelProvider(this, viewModelFactory)[CurrencyListViewModel::class.java]

        loadData(System.currentTimeMillis())
        viewModelObserve()

        setDefaultView()
    }

    private fun setCustomActionBar() {
        supportActionBar?.let {
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setCustomView(R.layout.custom_action_bar)
            it.customView.findViewById<TextView>(R.id.textViewTitleActionBar).text = "Валюты"
            it.customView.findViewById<ImageView>(R.id.imageViewBack).visibility = View.GONE
        }
    }

    private fun setRecyclerView() {
        adapter = CurrencyInfoAdapter(this)
        binding.recyclerViewCurrency.adapter = adapter
        binding.recyclerViewCurrency.layoutManager = GridLayoutManager(
            this,
            3,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    private fun setDefaultView() {
        binding.textInputEditDate.setText(getDateAsString(System.currentTimeMillis()))
    }

    private fun viewModelObserve() {
        with(viewModel) {
            currencyInfoLiveData.observe(this@CurrencySelectionActivity) {
                adapter.submitList(it)
            }

            isLoadData.observe(this@CurrencySelectionActivity) {
                binding.recyclerViewCurrency.isVisible = it
                binding.progressBarLoadData.isVisible = !it
            }

            errorLoadData.observe(this@CurrencySelectionActivity) {
                binding.recyclerViewCurrency.isVisible = !it
                binding.textViewErrorLoadData.isVisible = it
            }
        }
    }

    private fun loadData(date: Long) {
        viewModel.getListCurrency(date)
    }

    private fun launchConverterActivity(currencyInfo: CurrencyInfo) {
        val intent = CurrencyConverterActivity.newIntent(
            this,
            currencyInfo
        )
        startActivity(intent)
    }

    private fun setInitialDate() {
        binding.textInputEditDate.setText(getDateAsString(calendar.timeInMillis))
        lifecycleScope.launch {
            loadData(calendar.timeInMillis)
        }
    }

    private fun getDateAsString(timeInMillis: Long) =
        DateUtils.formatDateTime(
            this,
            timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
}