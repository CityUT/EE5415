package com.example.hkdconvertorjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private EditText editTextHKD;
    private Button buttonConvert;
    private TextView textViewResult;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        editTextHKD = findViewById(R.id.editTextHKD);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Create an ExecutorService for background tasks
        executorService = Executors.newSingleThreadExecutor();

        // Set click listener for the convert button
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        // Get the HKD amount from the input field
        final String hkdAmount = editTextHKD.getText().toString();
        if (hkdAmount.isEmpty()) {
            textViewResult.setText("Please enter an amount");
            return;
        }

        // Perform the conversion in a background thread
        // Perform the conversion in a background thread
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
                    final double rate = cnyObject.getDouble("rate");

                    // Calculate the converted amount
                    final double hkd = Double.parseDouble(hkdAmount);
                    final double cny = hkd * rate;

                    // Update the UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String result = String.format("%.2f HKD = %.2f CNY\nExchange rate: %.4f", hkd, cny, rate);
                            textViewResult.setText(result);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult.setText("Error: " + e.getMessage());
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the ExecutorService when the activity is destroyed
        executorService.shutdown();
    }
}