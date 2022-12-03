package com.crater.android.feature.invoicing.ui.screen.tracker.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePaymentTrackerViewModel @Inject constructor(
    private val repository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    cacheManager: CacheManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePaymentTrackerUiState())
    val uiState = _uiState.asStateFlow()

    private var isCustomersUpdated = false

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }


    init {
        cacheManager.getUserDetails().onEach { user ->
            _uiState.update { it.copy(fromUser = user.userName.name) }
        }.launchIn(viewModelScope)
    }

    fun onTrackerNumberChanged(number: String) {
        _uiState.update { it.copy(trackerNumber = number, hasTrackerNumberError = false) }
    }

    fun onFromUserChanged(user: String) {
        _uiState.update { it.copy(fromUser = user, hasFromUserError = false) }
    }

    fun onToCustomerChanged(customer: Customer) {
        _uiState.update { it.copy(toCustomer = customer, hasToCustomerError = false) }
    }

    fun onAmountChanged(amount: String) {
        _uiState.update { it.copy(amount = amount, hasAmountError = false) }
    }


    fun onDeleteCustomerClick() {
        val customerPhone = uiState.value.toCustomer?.phone ?: return
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            customerRepository.deleteCustomer(customerPhone)
            _uiState.update { it.copy(isLoading = false, toCustomer = null) }
        }
    }

    fun onSaveButtonClick() {
        if (validate()) {
            createTracker()
        }
    }

    private fun createTracker() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.createPaymentTracker(
                trackerNumber = uiState.value.trackerNumber,
                fromUser = uiState.value.fromUser,
                customer = uiState.value.toCustomer!!,
                amountDue = uiState.value.amount
            )
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }

    }

    private fun validate(): Boolean = with(uiState.value) {
        if (trackerNumber.isBlank()) {
            _uiState.update { it.copy(hasTrackerNumberError = true) }
            return@with false
        }

        if (fromUser.isBlank()) {
            _uiState.update { it.copy(hasFromUserError = true) }
            return@with false
        }

        if (toCustomer == null) {
            _uiState.update { it.copy(hasToCustomerError = true) }
            return@with false
        }

        if (amount.isBlank()) {
            _uiState.update { it.copy(hasAmountError = true) }
            return@with false
        }

        return@with true
    }


    fun onAddCustomerClick() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            isCustomersUpdated = customerRepository.fetchCustomers()
            _uiState.update { it.copy(isLoading = false, customerLoadedEvent = triggered) }
        }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onCustomerLoadedEventConsumed() {
        _uiState.update { it.copy(customerLoadedEvent = consumed) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

}