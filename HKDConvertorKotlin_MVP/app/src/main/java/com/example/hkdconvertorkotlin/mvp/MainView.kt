package com.example.hkdconvertorkotlin.mvp

// This interface defines the operations (methods) that the View (MainActivity) should implement.
// These methods will be used to update the UI, show results, and handle errors.
interface MainView {
    fun showLoading()  // Show a loading indicator when the conversion is in progress
    fun hideLoading()  // Hide the loading indicator once the conversion is complete
    fun showResult(result: String)  // Display the result of the currency conversion
    fun showError(error: String)  // Display an error message if something goes wrong
    fun showInputError()  // Show an error message if the input is invalid (e.g., empty input)
}