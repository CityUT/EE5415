package com.example.hkdconvertorkotlin.mvp

// This class implements the MainPresenter interface and acts as the Presenter in the MVP architecture.
// It handles user input, communicates with the Model, and updates the View.
class MainPresenterImpl(private val view: MainView, private val model: MainModel) : MainPresenter {

    // This method is triggered when the "Convert" button is clicked
    override fun onConvertButtonClicked(hkdAmount: String) {
        // Validate the input. If empty, show an input error in the View
        if (hkdAmount.isEmpty()) {
            view.showInputError()
            return
        }

        // Tell the View to show a loading indicator while conversion is in progress
        view.showLoading()

        // Request the Model to perform the currency conversion
        model.convertCurrency(hkdAmount, { result ->
            // On success, hide the loading indicator and show the result in the View
            view.hideLoading()
            view.showResult(result)
        }, { error ->
            // On error, hide the loading indicator and show the error message in the View
            view.hideLoading()
            view.showError(error)
        })
    }

    // This method can be used to clean up resources when the View is destroyed
    override fun onDestroy() {
        // Clean up any tasks if necessary (e.g., cancel ongoing coroutines)
    }
}