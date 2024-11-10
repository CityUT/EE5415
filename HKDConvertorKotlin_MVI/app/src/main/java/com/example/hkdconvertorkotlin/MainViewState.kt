package com.example.hkdconvertorkotlin

// This sealed class represents all possible states of the UI.
// It ensures that the UI can only be in one of the defined states.
sealed class MainViewState {
    // Idle state: when nothing is happening (initial state).
    object Idle : MainViewState()

    // Loading state: shown when the currency conversion is in progress.
    object Loading : MainViewState()

    // Success state: when the conversion finishes successfully.
    // The result will be displayed in the UI.
    data class Success(val result: String) : MainViewState()

    // Error state: when an error occurs during the conversion process.
    // It carries the error message to be shown in the UI.
    data class Error(val error: String) : MainViewState()
}