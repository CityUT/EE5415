package com.example.hkdconvertorkotlin.model

// Define the MainModel interface with a suspend function for currency conversion
interface MainModel {
    suspend fun convertCurrency(
        hkdAmount: String,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )
}