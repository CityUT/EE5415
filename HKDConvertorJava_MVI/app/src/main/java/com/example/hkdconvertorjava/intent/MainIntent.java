package com.example.hkdconvertorjava.intent;

public class MainIntent {

    // Intent representing a user action to convert currency
    public static class ConvertCurrency {
        public final String hkdAmount;  // The HKD amount to be converted

        // Constructor to initialize the HKD amount
        public ConvertCurrency(String hkdAmount) {
            this.hkdAmount = hkdAmount;
        }
    }
}