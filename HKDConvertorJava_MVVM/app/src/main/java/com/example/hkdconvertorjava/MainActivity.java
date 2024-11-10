// MainActivity.java
package com.example.hkdconvertorjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.hkdconvertorjava.viewmodel.ExchangeViewModel;

public class MainActivity extends AppCompatActivity {

    // UI elements for receiving input and displaying results
    private EditText editTextHKD;
    private Button buttonConvert;
    private TextView textViewResult;

    // ViewModel instance to handle data and business logic
    private ExchangeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD);  // Input field for HKD amount
        buttonConvert = findViewById(R.id.buttonConvert);  // Button to trigger the conversion
        textViewResult = findViewById(R.id.textViewResult);  // TextView to display the conversion result

        // Initialize ViewModel using ViewModelProvider
        viewModel = new ViewModelProvider(this).get(ExchangeViewModel.class);

        // Observe the result LiveData from the ViewModel and update the UI when it changes
        viewModel.getResultLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String result) {
                // Update the TextView with the conversion result
                textViewResult.setText(result);
            }
        });

        // Observe the error LiveData from the ViewModel and display any errors
        viewModel.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                // Display error messages if any
                textViewResult.setText(error);
            }
        });

        // Set a click listener for the convert button
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered HKD amount from the EditText and pass it to the ViewModel for conversion
                String hkdAmount = editTextHKD.getText().toString();
                viewModel.convertCurrency(hkdAmount);  // Trigger the conversion process in the ViewModel
            }
        });
    }
}