package uri.pm.conversordemoedas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.core.widget.doOnTextChanged

class MainActivity : AppCompatActivity() {

    var baseCurrency = Currencies.USD
    var toCurrency = Currencies.USD

    private val conversionService = ConversionService()
    private var amount = 1.0
    private lateinit var textResult: TextView
    private lateinit var textAmount: TextView

    private val currencyMap: Map<String, Currencies> = mapOf(
        "Dólar Americano" to Currencies.USD,
        "Real Brasileiro" to Currencies.BRL,
        "Bitcoin" to Currencies.BTC,
        "Peso Argentino" to Currencies.ARS,
        "Euro" to Currencies.EUR
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerBase = findViewById<Spinner>(R.id.spinnerBaseCurrency)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerToCurrency)
        textResult = findViewById(R.id.result)
        textAmount = findViewById(R.id.textAmount)
        textAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {updateConversion()}
        })

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.spinner_currencies,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerBase.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = parentView?.getItemAtPosition(position).toString()
                val selectedEnum = currencyMap[selectedCurrency]
                if (selectedEnum != null) {
                    toCurrency = selectedEnum
                    updateConversion()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        spinnerBase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = parentView?.getItemAtPosition(position).toString()
                val selectedEnum = currencyMap[selectedCurrency]
                if (selectedEnum != null) {
                    baseCurrency = selectedEnum
                    updateConversion()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

    }

    fun updateConversion() {
        try {
            amount = textAmount.text.toString().toDouble()
        } catch (_: Exception) {}

        val result = conversionService.convert(baseCurrency, toCurrency, amount)
        textResult.text = ("~= " + String.format("%.2f", result)).replace(".", ",")
    }

}