package com.example.hkdconvertorkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// The MainActivity represents the "View" in the MVI architecture.
// It observes the state from the ViewModel and updates the UI accordingly.
class MainActivity : AppCompatActivity() {

    // UI elements: Input field for HKD amount, a button to trigger conversion, and a text view to show the result.
    private lateinit var editTextHKD: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView

    // ViewModel instance, using the AndroidX ViewModel library.
    // ViewModels survive configuration changes such as screen rotations.
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        // Observe ViewModel state changes.
        observeViewModel()

        // Set a click listener on the convert button.
        // When clicked, it sends the ConvertCurrency intent to the ViewModel.
        buttonConvert.setOnClickListener {
            val hkdAmount = editTextHKD.text.toString()

            // Send the user's intent (ConvertCurrency) to the ViewModel.
            viewModel.handleIntent(MainIntent.ConvertCurrency(hkdAmount))
        }
    }

    // This function observes the state from the ViewModel and updates the UI accordingly.
    // It uses the LifecycleScope to collect the state changes in a coroutine.
    private fun observeViewModel() {
        lifecycleScope.launch {
            // Collect the state from the ViewModel's state flow.
            viewModel.state.collect { state ->
                // Update the UI based on the current state.
                when (state) {
                    is MainViewState.Idle -> {
                        // Idle state: clear the result text.
                        textViewResult.text = ""
                    }
                    is MainViewState.Loading -> {
                        // Loading state: show a loading message.
                        textViewResult.text = "Converting..."
                    }
                    is MainViewState.Success -> {
                        // Success state: show the conversion result.
                        textViewResult.text = state.result
                    }
                    is MainViewState.Error -> {
                        // Error state: show the error message.
                        textViewResult.text = state.error
                    }
                }
            }
        }
    }
}