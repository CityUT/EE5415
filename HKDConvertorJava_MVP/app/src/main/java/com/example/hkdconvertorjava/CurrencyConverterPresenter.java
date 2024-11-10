package com.example.hkdconvertorjava;

import android.os.Handler;
import android.os.Looper;

public class CurrencyConverterPresenter implements CurrencyConverterContract.Presenter {

    // Reference to the View (MainActivity) to update the UI
    private CurrencyConverterContract.View view;

    // Reference to the Model that handles the currency conversion
    private CurrencyConverterModel model;

    // Handler to switch between background threads and the main thread
    private Handler mainHandler;

    // Constructor that takes the View as an argument
    public CurrencyConverterPresenter(CurrencyConverterContract.View view) {
        this.view = view;  // Assign the passed View (MainActivity)
        this.model = new CurrencyConverterModel();  // Initialize the Model
        this.mainHandler = new Handler(Looper.getMainLooper());  // Handler for posting updates to the main thread
    }

    // Method to convert the currency (called by the View)
    @Override
    public void convertCurrency(String hkdAmount) {
        // If no amount is entered, show an error message
        if (hkdAmount.isEmpty()) {
            view.showError("Please enter an amount");
            return;
        }

        // Show loading indicator while conversion is in progress
        view.showLoading();

        // Call the Model to fetch the exchange rate and convert the currency
        model.fetchExchangeRate(hkdAmount, new CurrencyConverterModel.OnCurrencyConvertedListener() {
            @Override
            public void onSuccess(final String result) {
                // Switch to the main thread to update the UI with the result
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();  // Hide the loading indicator
                        view.showResult(result);  // Show the conversion result
                    }
                });
            }

            @Override
            public void onError(final String error) {
                // Switch to the main thread to update the UI with the error
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.hideLoading();  // Hide the loading indicator
                        view.showError(error);  // Show the error message
                    }
                });
            }
        });
    }
}