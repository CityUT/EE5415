// data/ExchangeRateModel.java
package com.example.hkdconvertorjava.data;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class ExchangeRateModel {

    // Fetches the exchange rate from the API
    public double fetchExchangeRate() throws Exception {
        // API URL to fetch HKD to CNY exchange rates
        URL url = new URL("https://www.floatrates.com/daily/hkd.json");

        // Open a secure HTTPS connection to the API
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        // Read the API response using BufferedReader
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        // Read the response line by line
        while ((line = reader.readLine()) != null) {
            response.append(line);  // Append each line to the response
        }
        reader.close();  // Close the reader

        // Parse the JSON response to get the CNY exchange rate
        JSONObject jsonObject = new JSONObject(response.toString());
        JSONObject cnyObject = jsonObject.getJSONObject("cny");

        // Return the exchange rate from HKD to CNY
        return cnyObject.getDouble("rate");
    }

    // Converts the HKD amount to CNY using the exchange rate
    public double convertHKDToCNY(double hkdAmount, double exchangeRate) {
        // Multiply the HKD amount by the exchange rate to get the CNY value
        return hkdAmount * exchangeRate;
    }
}