package com.example.hkdconvertorjava.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hkdconvertorjava.intent.MainIntent;
import com.example.hkdconvertorjava.model.MainModel;
import com.example.hkdconvertorjava.viewstate.MainViewState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {

    // Reference to the Model for performing the conversion
    private final MainModel mainModel = new MainModel();
    // ExecutorService for performing tasks in the background
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // LiveData to hold the current ViewState
    private final MutableLiveData<MainViewState> viewState = new MutableLiveData<>(new MainViewState.Idle());

    // Getter to expose the LiveData to the View
    public LiveData<MainViewState> getViewState() {
        return viewState;
    }

    // Method to process the ConvertCurrency intent (user action)
    public void processIntent(MainIntent.ConvertCurrency intent) {
        // Set the current state to Loading while the conversion is in progress
        viewState.setValue(new MainViewState.Loading());

        // Perform the conversion in a background thread
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Fetch the result from the Model
                    String result = mainModel.convertCurrency(intent.hkdAmount);
                    // Post the success state with the result
                    viewState.postValue(new MainViewState.Success(result));
                } catch (Exception e) {
                    // If an error occurs, post the error state with the error message
                    viewState.postValue(new MainViewState.Error(e.getMessage()));
                }
            }
        });
    }

    // Clean up resources when the ViewModel is cleared
    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();  // Shutdown the background executor
    }
}