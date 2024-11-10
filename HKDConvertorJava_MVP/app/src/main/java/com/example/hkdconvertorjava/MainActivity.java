package com.example.hkdconvertorjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements CurrencyConverterContract.View {

    // UI elements
    private EditText editTextHKD;
    private Button buttonConvert;
    private TextView textViewResult;

    // Presenter instance
    private CurrencyConverterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD);  // Input field for entering HKD amount
        buttonConvert = findViewById(R.id.buttonConvert);  // Button to trigger conversion
        textViewResult = findViewById(R.id.textViewResult);  // TextView to show the result or errors

        // Initialize the Presenter and pass this activity as the View
        presenter = new CurrencyConverterPresenter(this);

        // Set a click listener for the convert button
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the Presenter to handle the conversion when the button is clicked
                presenter.convertCurrency(editTextHKD.getText().toString());
            }
        });
    }

    // Implementation of CurrencyConverterContract.View methods:

    @Override
    public void showResult(String result) {
        // Update the TextView with the conversion result
        textViewResult.setText(result);
    }

    @Override
    public void showError(String error) {
        // Update the TextView with an error message
        textViewResult.setText("Error: " + error);
    }

    @Override
    public void showLoading() {
        // Show a loading message while the conversion is in progress
        textViewResult.setText("Loading...");
    }

    @Override
    public void hideLoading() {
        // Hide the loading indicator (if any)
        // This method can be used to hide progress bars or similar indicators
    }
}