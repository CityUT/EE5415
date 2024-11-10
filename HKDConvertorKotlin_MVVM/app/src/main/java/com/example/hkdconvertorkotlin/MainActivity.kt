package com.example.hkdconvertorkotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels   // Ensure correct import
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.hkdconvertorkotlin.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    // Using viewModels delegate to get ViewModel instance
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Use findViewById to reference views from the layout
        val editTextHKD = findViewById<EditText>(R.id.editTextHKD)
        val buttonConvert = findViewById<Button>(R.id.buttonConvert)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        // Observe conversionResult LiveData from ViewModel and update the UI
        viewModel.conversionResult.observe(this, Observer { result ->
            textViewResult.text = result
        })

        // Set up a click listener for the button
        buttonConvert.setOnClickListener {
            val hkdAmount = editTextHKD.text.toString()
            viewModel.convertCurrency(hkdAmount)
        }
    }
}