package com.example.hkdconvertorjava;

public interface CurrencyConverterContract {

    // View interface for the View component in MVP
    interface View {
        // Display the conversion result
        void showResult(String result);

        // Display any error messages
        void showError(String error);

        // Show a loading indicator while the conversion is happening
        void showLoading();

        // Hide the loading indicator once the conversion is done
        void hideLoading();
    }

    // Presenter interface for the Presenter component in MVP
    interface Presenter {
        // Convert the currency based on the provided HKD amount
        void convertCurrency(String hkdAmount);
    }
}