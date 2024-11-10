package com.example.hkdconvertorjava;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.net.ssl.HttpsURLConnection;

public class CurrencyConverterModel {

    // Executor service for performing network operations on a background thread
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Method to fetch exchange rate and calculate the conversion
    public void fetchExchangeRate(final String hkdAmount, final OnCurrencyConvertedListener listener) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Fetch the exchange rate from the API using HTTPS
                    URL url = new URL("https://www.floatrates.com/daily/hkd.json");
                    HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject cnyObject = jsonObject.getJSONObject("cny");
                    double rate = cnyObject.getDouble("rate");

                    // Calculate the converted amount
                    double hkd = Double.parseDouble(hkdAmount);
                    double cny = hkd * rate;
                    String result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate);

                    // Callback with result
                    listener.onSuccess(result);
                } catch (Exception e) {
                    // Callback with error message
                    listener.onError(e.getMessage());
                }
            }
        });
    }

    // Callback interface to return the result or error back to the Presenter
    public interface OnCurrencyConvertedListener {
        void onSuccess(String result);
        void onError(String error);
    }
}