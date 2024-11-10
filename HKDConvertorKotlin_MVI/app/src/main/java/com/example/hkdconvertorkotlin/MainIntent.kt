package com.example.hkdconvertorkotlin

// This sealed class represents the user's intent (actions).
// It defines what kind of actions the user can trigger.
sealed class MainIntent {
    // This intent represents the action to convert a currency.
    // It carries the HKD amount input by the user.
    data class ConvertCurrency(val hkdAmount: String) : MainIntent()
}