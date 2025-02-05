package com.example.currencyconversor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MoedaListAdapter (context: Context, moedas: List<Moeda>) :
    ArrayAdapter<Moeda>(context, 0, moedas) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup) : View{
            return flagView(position, convertView, parent)
        }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return flagView(position, convertView, parent)
    }

    private fun flagView (position: Int, convertView: View?, parent: ViewGroup): View{
        val moeda = getItem(position)
        val view = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        val flagIcon = view.findViewById<ImageView>(R.id.iv_flag)
        val currencyName = view.findViewById<TextView>(R.id.tv_currency_name)

        moeda?.let {
            flagIcon.setImageResource(it.flag)
            currencyName.text =it.name
        }
        return view
    }
}