package com.example.hkdconvertorkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hkdconvertorkotlin.mvp.MainModelImpl
import com.example.hkdconvertorkotlin.mvp.MainPresenter
import com.example.hkdconvertorkotlin.mvp.MainPresenterImpl
import com.example.hkdconvertorkotlin.mvp.MainView

// MainActivity now implements the MainView interface, making it a part of the MVP architecture as the View.
// It handles UI updates and user interactions.
class MainActivity : AppCompatActivity(), MainView {

    private lateinit var editTextHKD: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)

        // Initialize the Presenter with this View (MainActivity) and the Model implementation
        presenter = MainPresenterImpl(this, MainModelImpl())

        // Set the click listener for the Convert button to delegate the action to the Presenter
        buttonConvert.setOnClickListener {
            presenter.onConvertButtonClicked(editTextHKD.text.toString())
        }
    }

    // Show a loading message while the conversion is in progress
    override fun showLoading() {
        textViewResult.text = "Loading..."
    }

    // Hide the loading state (in this case we don't need to explicitly hide anything)
    override fun hideLoading() {
        // No need to hide anything since we're directly updating the result
    }

    // Display the conversion result in the TextView
    override fun showResult(result: String) {
        textViewResult.text = result
    }

    // Display any errors that occur during the conversion process
    override fun showError(error: String) {
        textViewResult.text = "Error: $error"
    }

    // Show an error if the user input is invalid (e.g., empty string)
    override fun showInputError() {
        textViewResult.text = "Please enter an amount"
    }

    // Clean up resources when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()  // Let the Presenter clean up any necessary resources
    }
}