package com.example.hkdconvertorjava.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.hkdconvertorjava.R;
import com.example.hkdconvertorjava.intent.MainIntent;
import com.example.hkdconvertorjava.viewstate.MainViewState;
import com.example.hkdconvertorjava.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    // UI components
    private EditText editTextHKD;
    private Button buttonConvert;
    private TextView textViewResult;

    // ViewModel instance
    private final MainViewModel mainViewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        editTextHKD = findViewById(R.id.editTextHKD);  // Input field for HKD amount
        buttonConvert = findViewById(R.id.buttonConvert);  // Button to trigger conversion
        textViewResult = findViewById(R.id.textViewResult);  // TextView to display result or errors

        // Observe changes in the ViewState and update the UI accordingly
        mainViewModel.getViewState().observe(this, new Observer<MainViewState>() {
            @Override
            public void onChanged(MainViewState state) {
                if (state instanceof MainViewState.Loading) {
                    // Show loading message when the conversion is in progress
                    textViewResult.setText("Loading...");
                } else if (state instanceof MainViewState.Success) {
                    // Show the conversion result when the conversion is successful
                    String result = ((MainViewState.Success) state).result;
                    textViewResult.setText(result);
                } else if (state instanceof MainViewState.Error) {
                    // Show the error message if an error occurs
                    String error = ((MainViewState.Error) state).error;
                    textViewResult.setText("Error: " + error);
                }
            }
        });

        // Set a click listener for the conversion button
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the HKD amount entered by the user
                String hkdAmount = editTextHKD.getText().toString();
                // If the input is not empty, send the intent to convert currency
                if (!hkdAmount.isEmpty()) {
                    mainViewModel.processIntent(new MainIntent.ConvertCurrency(hkdAmount));
                } else {
                    textViewResult.setText("Please enter an amount");
                }
            }
        });
    }
}