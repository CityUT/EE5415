// viewmodel/ExchangeViewModel.java
package com.example.hkdconvertorjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hkdconvertorjava.data.ExchangeRateModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeViewModel extends ViewModel {

    // Model for fetching exchange rates and performing conversions
    private final ExchangeRateModel model;

    // ExecutorService for performing tasks in the background
    private final ExecutorService executorService;

    // LiveData for exposing the conversion result to the View
    private final MutableLiveData<String> resultLiveData;

    // LiveData for exposing any errors that occur during conversion
    private final MutableLiveData<String> errorLiveData;

    // Constructor initializes the model and background task executor
    public ExchangeViewModel() {
        model = new ExchangeRateModel();  // Initialize the model that handles data fetching and conversion
        executorService = Executors.newSingleThreadExecutor();  // Create a single-threaded executor
        resultLiveData = new MutableLiveData<>();  // Initialize LiveData for results
        errorLiveData = new MutableLiveData<>();  // Initialize LiveData for errors
    }

    // Expose the result LiveData to the View (MainActivity)
    public LiveData<String> getResultLiveData() {
        return resultLiveData;
    }

    // Expose the error LiveData to the View (MainActivity)
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    // Method to convert HKD to CNY
    public void convertCurrency(String hkdAmount) {
        // Check if the input amount is valid
        if (hkdAmount == null || hkdAmount.isEmpty()) {
            resultLiveData.postValue("Please enter an amount");  // Notify the View with an error message
            return;
        }

        try {
            // Convert the input string to a double value (HKD amount)
            final double hkd = Double.parseDouble(hkdAmount);

            // Perform the network request and conversion in a background thread
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Fetch the exchange rate (from HKD to CNY)
                        double rate = model.fetchExchangeRate();

                        // Convert the HKD amount to CNY using the exchange rate
                        double cny = model.convertHKDToCNY(hkd, rate);

                        // Format the result string and post it to the LiveData
                        String result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate);
                        resultLiveData.postValue(result);  // Update the result LiveData with the conversion result

                    } catch (Exception e) {
                        // Post an error message if something goes wrong
                        errorLiveData.postValue("Error: " + e.getMessage());
                    }
                }
            });
        } catch (NumberFormatException e) {
            // Post an error message if the input is not a valid number
            resultLiveData.postValue("Invalid amount");
        }
    }

    // Called when the ViewModel is cleared, shutdown the executor service
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();  // Shutdown the background executor to prevent memory leaks
    }
}