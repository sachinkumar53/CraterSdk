package com.crater.android.feature.invoicing.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import com.crater.android.feature.invoicing.ui.model.CustomerListState
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CustomerListState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val error = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(error)) }
        Log.e(TAG, "Coroutine exception caught: ", throwable)
    }

    init {
        fetchCustomers()

        /*repository.getCustomers().onEach { list ->
            _uiState.update { it.copy(data = list) }
        }*/

    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    fun fetchCustomers() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(exceptionHandler) {
            val response = repository.fetchCustomers()
            //_uiState.update { it.copy(isLoading = false, data = response) }
        }
    }

    /*fun addCustomer(customer: Customer) {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.addCustomer(customer)

        }
    }

    fun updateCustomer(customer: Customer) {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.updateCustomer(customer)

        }
    }

    fun deleteCustomer(phone: String) {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.deleteCustomer(phone)

        }
    }*/
}

private const val TAG = "CustomerViewModel"