package com.example.hkdconvertorkotlin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import java.io.BufferedReader
import java.io.InputStreamReader

class MainViewModel : ViewModel() {

    // A MutableStateFlow that holds the current state of the UI.
    // Initially, it's set to 'Idle', meaning no action has been taken yet.
    private val _state = MutableStateFlow<MainViewState>(MainViewState.Idle)

    // Publicly exposed StateFlow which allows the UI to observe the state changes.
    // Unlike MutableStateFlow, this is immutable, so it can only be read from outside the ViewModel.
    val state: StateFlow<MainViewState> get() = _state

    // This function handles different user intents (actions).
    // It takes a 'MainIntent' object as input and performs the appropriate action based on the intent.
    fun handleIntent(intent: MainIntent) {
        when (intent) {
            // If the user wants to convert currency, call the 'convertCurrency' function
            // with the amount of HKD (Hong Kong Dollars) they provided.
            is MainIntent.ConvertCurrency -> {
                convertCurrency(intent.hkdAmount)
            }
        }
    }

    // This function is responsible for converting the given HKD amount to CNY (Chinese Yuan).
    // It performs input validation, then launches a coroutine to handle the network request.
    private fun convertCurrency(hkdAmount: String) {
        // If the input amount is empty, set the state to an error state with a message
        // and return early (i.e., do nothing further).
        if (hkdAmount.isEmpty()) {
            _state.value = MainViewState.Error("Please enter an amount")
            return
        }

        // Launch a coroutine in the ViewModel's scope to perform the network request.
        // viewModelScope ensures that the coroutine is tied to the ViewModel's lifecycle,
        // meaning it will be automatically canceled if the ViewModel is cleared.
        viewModelScope.launch {
            // Set the UI state to 'Loading' indicating to the UI that the data is being fetched.
            _state.value = MainViewState.Loading

            try {
                // Perform the network request on the IO dispatcher (a background thread).
                // This is where the actual HTTP request to the API happens.
                val response = withContext(Dispatchers.IO) {
                    // URL of the API that provides exchange rate data for HKD.
                    val url = URL("https://www.floatrates.com/daily/hkd.json")
                    // Open a connection to the URL and cast it as an HttpsURLConnection.
                    val connection = url.openConnection() as HttpsURLConnection
                    // Use a BufferedReader to read the response from the API.
                    BufferedReader(InputStreamReader(connection.inputStream)).use { reader ->
                        // Convert the response into a single string.
                        reader.readText()
                    }
                }

                // Parse the JSON response to extract the exchange rate for CNY.
                val jsonObject = JSONObject(response)
                val cnyObject = jsonObject.getJSONObject("cny")
                val rate = cnyObject.getDouble("rate")

                // Convert the input HKD amount to a double.
                val hkd = hkdAmount.toDouble()
                // Multiply the HKD amount by the exchange rate to get the equivalent in CNY.
                val cny = hkd * rate
                // Format the result into a user-friendly string.
                val result = String.format(
                    "%.2f HKD = %.2f CNY\nExchange rate: %.4f",
                    hkd, cny, rate
                )

                // Update the state to 'Success' and pass the formatted result string.
                _state.value = MainViewState.Success(result)

            } catch (e: Exception) {
                // If any exception occurs (e.g., network error, JSON parsing error),
                // catch it and set the state to 'Error' with the exception message.
                e.printStackTrace()
                _state.value = MainViewState.Error("Error: ${e.message}")
            }
        }
    }
}