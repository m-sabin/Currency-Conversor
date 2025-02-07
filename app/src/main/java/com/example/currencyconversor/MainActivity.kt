package com.example.currencyconversor

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinner = findViewById<Spinner>(R.id.spinner_moedas)
        val spinnerConvert = findViewById<Spinner>(R.id.spinner_moedas_convert)
        val valueSpinner = findViewById<TextInputEditText>(R.id.tie_amount)
        val valueSpinnerConvert = findViewById<TextInputEditText>(R.id.tie_convert_amount)
        val btnSwap = findViewById<ImageButton>(R.id.btn_swap)
        val btnClean = findViewById<Button>(R.id.btn_clean)
        val tvIndicativeRate = findViewById<TextView>(R.id.tv_indicativeRate)

        val moedas = listOf(
            Moeda("BRL", R.drawable.ic_brazil_brl),
            Moeda("USD", R.drawable.ic_usa_us),
            Moeda("CAD", R.drawable.ic_canada_cad),
            Moeda("AUD", R.drawable.ic_autralie_gbp),
            Moeda("GBP", R.drawable.ic_england_gbp),
            Moeda("EUR", R.drawable.ic_europe_eur),
            Moeda("JPY", R.drawable.ic_japon_jpy),
            Moeda("CNY", R.drawable.ic_china_cny),
            Moeda("NGN", R.drawable.ic_nigeria_naira),
            Moeda("XOF", R.drawable.ic_benin_fcfa),
        )

        val adapter = MoedaListAdapter(this, moedas)
        spinner.adapter = adapter
        spinnerConvert.adapter = adapter

        valueSpinner.text.toString()
        valueSpinnerConvert.text.toString()

        val exchangeRates = mapOf(
            "USD" to 1.0,
            "BRL" to 5.79,
            "CAD" to 1.43,
            "AUD" to 1.59,
            "GBP" to 0.80,
            "EUR" to 0.96,
            "JPY" to 152.52,
            "CNY" to 7.27,
            "NGN" to 1497.67,
            "XOF" to 630.26

        )

        val defaultFromCurrency = "USD"
        val defaultToCurrency = "BRL"

        val fromCurrencyPosition = moedas.indexOfFirst { it.name == defaultFromCurrency }
        val toCurrencyPosition = moedas.indexOfFirst { it.name == defaultToCurrency }

        if (fromCurrencyPosition != -1) spinner.setSelection(fromCurrencyPosition)
        if (toCurrencyPosition != -1) spinnerConvert.setSelection(toCurrencyPosition)

        fun updateIndicativeRate() {
            val fromCurrency = (spinner.selectedItem as Moeda).name
            val toCurrency = (spinnerConvert.selectedItem as Moeda).name

            val fromRate = exchangeRates[fromCurrency] ?: return
            val toRate = exchangeRates[toCurrency] ?: return

            tvIndicativeRate.text =
                "1 $fromCurrency = ${"%.2f".format(toRate / fromRate)} $toCurrency"
        }

        fun convertCurrency() {
            val inputValue = valueSpinner.text.toString().toDoubleOrNull() ?: 0.0
            val fromCurrency = (spinner.selectedItem as Moeda).name
            val toCurrency = (spinnerConvert.selectedItem as Moeda).name

            val fromRate = exchangeRates[fromCurrency] ?: return
            val toRate = exchangeRates[toCurrency] ?: return

            if (inputValue != null) {
                val valueInUSD = inputValue / fromRate
                val convertValue = valueInUSD * toRate

                valueSpinnerConvert.setText("%.2f".format(convertValue))

            } else {
                valueSpinnerConvert.text?.clear()
            }
            updateIndicativeRate()
        }
        valueSpinner.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    valueSpinnerConvert.text?.clear()
                } else {
                    convertCurrency()
                }
            }
        })

        val currencyChangeListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinner.onItemSelectedListener = currencyChangeListener
        spinnerConvert.onItemSelectedListener = currencyChangeListener

        btnSwap.setOnClickListener {
            val swapping = valueSpinner.text
            valueSpinner.text = valueSpinnerConvert.text
            valueSpinnerConvert.text = swapping

            val moedasPosition = spinner.selectedItemPosition
            spinner.setSelection(spinnerConvert.selectedItemPosition)
            spinnerConvert.setSelection(moedasPosition)

        }

        btnClean.setOnClickListener {
            valueSpinner.text?.clear()
            valueSpinnerConvert.text?.clear()
        }
    }
}




