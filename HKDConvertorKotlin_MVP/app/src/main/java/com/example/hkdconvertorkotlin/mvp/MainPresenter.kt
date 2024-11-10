package com.example.hkdconvertorkotlin.mvp

// This interface defines the operations that the Presenter should handle.
// The Presenter is responsible for processing user input and providing data to the View.
interface MainPresenter {
    fun onConvertButtonClicked(hkdAmount: String)  // Handle the "Convert" button click and start the conversion
    fun onDestroy()  // Clean up resources when the View is destroyed
}