package com.example.hkdconvertorkotlin.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// MainModelImpl implements the MainModel interface and handles the currency conversion logic.
class MainModelImpl : MainModel {

    // Ensure the method signature matches the MainModel interface
    override suspend fun convertCurrency(
        hkdAmount: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            // Execute network request in IO dispatcher
            val response = withContext(Dispatchers.IO) {
                val url = URL("https://www.floatrates.com/daily/hkd.json")
                val connection = url.openConnection() as HttpsURLConnection
                BufferedReader(InputStreamReader(connection.inputStream)).use {
                    it.readText()
                }
            }

            // Parse the JSON response to get the exchange rate
            val jsonObject = JSONObject(response)
            val cnyObject = jsonObject.getJSONObject("cny")
            val rate = cnyObject.getDouble("rate")

            val hkd = hkdAmount.toDouble()
            val cny = hkd * rate
            val result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate)

            // Call success callback with result
            onSuccess(result)

        } catch (e: Exception) {
            // Call error callback with error message
            onError(e.message ?: "Unknown error")
        }
    }
}