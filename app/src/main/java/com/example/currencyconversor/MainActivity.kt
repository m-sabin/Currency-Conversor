package com.example.currencyconversor

import android.os.Bundle
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
//        val iconFlag = findViewById<ImageView>(R.id.iv_icon_flag)
        val moedas = listOf(
            Moeda("BRL", R.drawable.ic_brazil_brl),
            Moeda("USA", R.drawable.ic_usa_us),
            Moeda("CAD", R.drawable.ic_canada_cad),
            Moeda("AUD", R.drawable.ic_autralie_gbp),
            Moeda("GBP", R.drawable.ic_england_gbp),
            Moeda("EUR", R.drawable.ic_europe_eur),
            Moeda("JPY", R.drawable.ic_japon_jpy),
            Moeda("CHY", R.drawable.ic_china_cny),
            Moeda("NGN", R.drawable.ic_nigeria_naira),
            Moeda("FCFA", R.drawable.ic_benin_fcfa),
            )

        val adapter = MoedaListAdapter(this, moedas)
        spinner.adapter = adapter
        spinnerConvert.adapter = adapter

        valueSpinner.text.toString()
        valueSpinnerConvert.text.toString()

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




