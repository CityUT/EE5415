package com.example.hkdconvertorkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hkdconvertorkotlin.model.MainModel
import com.example.hkdconvertorkotlin.model.MainModelImpl  // 确保正确导入
import kotlinx.coroutines.launch

// ViewModel class responsible for handling data and business logic in the MVVM architecture.
class MainViewModel : ViewModel() {

    // LiveData to hold the conversion result
    private val _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String> get() = _conversionResult

    // LiveData to control the loading state
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // Initialize the model (MainModelImpl is the implementation of the MainModel interface)
    private val model: MainModel = MainModelImpl()

    // Function to convert currency, which is triggered by the UI
    fun convertCurrency(hkdAmount: String) {
        // Validate the input
        if (hkdAmount.isEmpty()) {
            _conversionResult.value = "Please enter an amount"
            return
        }

        // Set loading state to true
        _isLoading.value = true

        // Launch a coroutine to perform the currency conversion
        viewModelScope.launch {
            model.convertCurrency(hkdAmount, { result: String ->
                // On success, update the LiveData with the result
                _conversionResult.value = result
                _isLoading.value = false
            }, { errorMsg: String ->
                // On error, update the LiveData with the error message
                _conversionResult.value = "Error: $errorMsg"
                _isLoading.value = false
            })
        }
    }
}