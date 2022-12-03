package com.crater.android.feature.invoicing.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import com.crater.android.feature.invoicing.ui.model.EditCustomerArgs
import com.crater.android.feature.invoicing.ui.screen.addeditcustomer.EditCustomerUiState
import com.crater.android.feature.navArgs
import com.crater.android.model.ErrorHandle
import com.crater.android.utils.BaseViewModel
import com.crater.android.utils.CraterParsers
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class EditCustomerViewModel @Inject constructor(
    private val repository: CustomerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args: EditCustomerArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(EditCustomerUiState())
    val uiState = _uiState.asStateFlow()

    var isNameError by mutableStateOf(false)
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        var error = BaseViewModel.FALLBACK_ERROR_MESSAGE
        if (throwable is HttpException) {
            val json = throwable.response()?.errorBody()?.string()
            val craterErrorResponse = CraterParsers.gson.fromJson(json, ErrorHandle::class.java)
            craterErrorResponse.detail.also { error = it }
        }
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(error)) }
        Log.e(TAG, "Coroutine exception caught: ", throwable)
    }

    init {
        args.customer?.let { customer ->
            _uiState.update {
                it.copy(
                    name = customer.name,
                    phone = customer.phone,
                    emailId = customer.emailId,
                    gst = customer.gst,
                    city = customer.city,
                    pinCode = customer.pinCode,
                    pan = customer.pan,
                    address = customer.address,
                    state = customer.state,
                    country = customer.country,
                    isEditMode = args.isEditMode
                )
            }
        }
    }


    fun onNameChanged(value: String) {
        _uiState.update { it.copy(name = value) }
        isNameError = false
    }

    fun onPhoneChanged(value: String) {
        _uiState.update { it.copy(phone = value) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(emailId = value) }
    }

    /*fun onPANChanged(value: String) {
        _uiState.update { it.copy(pan = value) }
    }*/

    fun onGstChanged(value: String) {
        _uiState.update { it.copy(gst = value) }
    }

    fun onAddressChanged(value: String) {
        _uiState.update { it.copy(address = value) }
    }

    fun onCityChanged(value: String) {
        _uiState.update { it.copy(gst = value) }
    }

    fun onStateChanged(value: String) {
        _uiState.update { it.copy(state = value) }
    }

    fun onCountryChanged(value: String) {
        _uiState.update { it.copy(country = value) }
    }

    fun onPinCodeChanged(value: String) {
        _uiState.update { it.copy(pinCode = value) }
    }

    /*fun onDeleteClick() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            repository.deleteCustomer(customer!!.phone)
            _uiState.update { it.copy(isLoading = false, deleteSuccessEvent = triggered) }
        }
    }*/

    fun onUpdateClick() {
        if (uiState.value.name.isEmpty()) {
            isNameError = true
            return
        }

        val customer = with(uiState.value) {
            Customer(
                name = name,
                phone = phone,
                emailId = emailId,
                gst = gst,
                city = city,
                pinCode = pinCode,
                pan = pan,
                address = address,
                state = state,
                country = country
            )
        }
        updateCustomer(customer, args.isEditMode)
    }

    /*fun onDeleteSuccessEventConsumed() {
        _uiState.update { it.copy(deleteSuccessEvent = consumed) }
    }*/

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    private fun updateCustomer(customer: Customer, isEditMode: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            if (isEditMode) {
                repository.updateCustomer(customer)
            } else {
                repository.addCustomer(customer)
            }
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(customer)) }
        }
    }

    /*fun deleteCustomer(phone: String) {
        viewModelScope.launch(exceptionHandler) {
            val response = repository.deleteCustomer(phone)

        }
    }*/
}

private const val TAG = "CustomerViewModel"