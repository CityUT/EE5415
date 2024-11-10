package com.example.hkdconvertorkotlin.mvp

// This interface defines the behavior of the Model for fetching the exchange rate and performing the conversion.
// The Model will return data to the Presenter via success or error callbacks.
interface MainModel {
    fun convertCurrency(hkdAmount: String, onSuccess: (String) -> Unit, onError: (String) -> Unit)
}