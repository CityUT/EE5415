package com.example.hkdconvertorjava.model;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class MainModel {

    // Method for converting the HKD amount to CNY by fetching the exchange rate from the API
    public String convertCurrency(String hkdAmount) throws Exception {
        // API endpoint to fetch exchange rates
        URL url = new URL("https://www.floatrates.com/daily/hkd.json");
        // Open a secure HTTPS connection to the API
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Read the API response using BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        // Read the entire response line by line
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();  // Close the reader

        // Parse the JSON response to extract the CNY exchange rate
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject cnyObject = jsonObject.getJSONObject("cny");
        double rate = cnyObject.getDouble("rate");

        // Convert the input HKD amount to a double and calculate the CNY equivalent
        double hkd = Double.parseDouble(hkdAmount);
        double cny = hkd * rate;

        // Return a formatted string showing the conversion result and exchange rate
        return String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate);
    }
}