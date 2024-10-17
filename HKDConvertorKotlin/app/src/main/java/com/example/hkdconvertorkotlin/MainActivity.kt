package com.example.hkdconvertorkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var editTextHKD: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        // Set click listener for the convert button
        buttonConvert.setOnClickListener {
            convertCurrency()
        }
    }

    private fun convertCurrency() {
        // Get the HKD amount from the input field
        val hkdAmount = editTextHKD.text.toString()
        if (hkdAmount.isEmpty()) {
            textViewResult.text = "Please enter an amount"
            return
        }

        // Perform the conversion in a coroutine
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Fetch the exchange rate from the API using HTTPS
                val response = withContext(Dispatchers.IO) {
                    val url = URL("https://www.floatrates.com/daily/hkd.json")
                    val connection = url.openConnection() as HttpsURLConnection
                    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                        reader.readText()
                    }
                }

                // Parse the JSON response
                val jsonObject = JSONObject(response)
                val cnyObject = jsonObject.getJSONObject("cny")
                val rate = cnyObject.getDouble("rate")

                // Calculate the converted amount
                val hkd = hkdAmount.toDouble()
                val cny = hkd * rate

                // Update the UI
                val result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate)
                textViewResult.text = result

            } catch (e: Exception) {
                e.printStackTrace()
                textViewResult.text = "Error: ${e.message}"
            }
        }
    }
}