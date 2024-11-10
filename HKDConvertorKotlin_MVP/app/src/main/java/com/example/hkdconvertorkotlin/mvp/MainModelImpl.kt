package com.example.hkdconvertorkotlin.mvp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// Implementing the MainModel interface with proper coroutine handling.
class MainModelImpl : MainModel {

    // Mark the function as suspend to allow the use of withContext inside it.
    override fun convertCurrency(hkdAmount: String, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        // Use CoroutineScope to handle coroutine launching
        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Perform the network request on the IO thread
                val response = withContext(Dispatchers.IO) {
                    val url = URL("https://www.floatrates.com/daily/hkd.json")
                    val connection = url.openConnection() as HttpsURLConnection
                    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                        reader.readText()
                    }
                }

                // Parse the JSON response to get the exchange rate for CNY
                val jsonObject = JSONObject(response)
                val cnyObject = jsonObject.getJSONObject("cny")
                val rate = cnyObject.getDouble("rate")

                // Convert HKD to CNY using the fetched exchange rate
                val hkd = hkdAmount.toDouble()
                val cny = hkd * rate
                val result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate)

                // Invoke the success callback with the conversion result
                onSuccess(result)

            } catch (e: Exception) {
                e.printStackTrace()
                // If any exception occurs, invoke the error callback
                onError(e.message ?: "Unknown error")
            }
        }
    }
}