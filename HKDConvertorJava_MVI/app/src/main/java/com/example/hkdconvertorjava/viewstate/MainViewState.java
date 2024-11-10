package com.example.hkdconvertorjava.viewstate;

// Represents different states that the View can be in
public class MainViewState {

    // Idle state when no action is being performed
    public static class Idle extends MainViewState {}

    // Loading state when a conversion is in progress
    public static class Loading extends MainViewState {}

    // Success state when the conversion is successful
    public static class Success extends MainViewState {
        public final String result;  // Holds the conversion result

        // Constructor to initialize the result
        public Success(String result) {
            this.result = result;
        }
    }

    // Error state when an error occurs during the conversion process
    public static class Error extends MainViewState {
        public final String error;  // Holds the error message

        // Constructor to initialize the error message
        public Error(String error) {
            this.error = error;
        }
    }
}