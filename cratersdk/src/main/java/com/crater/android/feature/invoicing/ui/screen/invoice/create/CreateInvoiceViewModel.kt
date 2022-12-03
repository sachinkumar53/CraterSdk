package com.crater.android.feature.invoicing.ui.screen.invoice.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.dto.invoice.CreateInvoiceRequest
import com.crater.android.feature.invoicing.data.mapper.toDto
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.domain.model.Service
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
class CreateInvoiceViewModel @Inject constructor(
    private val repository: InvoiceRepository,
    private val customerRepository: CustomerRepository,
    cacheManager: CacheManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateInvoiceUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }

    private var isCustomersUpdated = false

    init {
        cacheManager.getUserDetails().onEach { user ->
            _uiState.update { it.copy(fromUser = user.userName.name) }
        }.launchIn(viewModelScope)
    }

    fun onInvoiceNumberChanged(number: String) {
        _uiState.update { it.copy(invoiceNumber = number, hasInvoiceNumberError = false) }
    }

    fun onFromUserChanged(user: String) {
        _uiState.update { it.copy(fromUser = user, hasFromUserError = false) }
    }

    fun onToCustomerChanged(customer: Customer) {
        _uiState.update { it.copy(toCustomer = customer, hasToCustomerError = false) }
    }

    fun onTaxChanged(tax: String) {
        val services = uiState.value.services
        val totalAmount = calculateTotalAmount(services, tax.toDoubleOrNull() ?: 0.0)
        _uiState.update {
            it.copy(
                tax = tax,
                amount = totalAmount.toString()
            )
        }
    }


    fun onAmountChanged(amount: String) {
        _uiState.update { it.copy(amount = amount, hasAmountError = false) }
    }


    fun onCreatedDateChanged(date: String) {
        _uiState.update { it.copy(creationDate = date) }
    }

    fun onDueDateChanged(date: String) {
        _uiState.update { it.copy(dueDate = date, hasDueDateError = false) }
    }

    fun onTaxTypeChanged(type: String) {
        _uiState.update { it.copy(taxType = type) }
    }

    fun onDeleteCustomerClick() {
        val customerPhone = uiState.value.toCustomer?.phone ?: return
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            customerRepository.deleteCustomer(customerPhone)
            _uiState.update { it.copy(isLoading = false, toCustomer = null) }
        }
    }

    fun onServiceAdded(service: Service) {
        val services = uiState.value.services + service
        val tax = uiState.value.tax.toDoubleOrNull() ?: 0.0
        val totalAmount = calculateTotalAmount(services, tax)
        _uiState.update {
            it.copy(
                services = services,
                amount = totalAmount.toString(),
                hasServicesError = false
            )
        }
    }

    private fun calculateTotalAmount(services: List<Service>, tax: Double): Double {
        return services.sumOf {
            it.price.toDouble() * it.quantity.toDouble()
        }.let {
            it + (it * tax / 100.0)
        }
    }

    fun onSaveButtonClick() {
        if (validate()) {
            createInvoice()
        }
    }

    private fun createInvoice() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val request = createRequest()
            val response = repository.createInvoice(request)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }

    }

    private fun createRequest(): CreateInvoiceRequest {
        return with(uiState.value) {
            CreateInvoiceRequest(
                invoiceNo = invoiceNumber,
                fromUser = fromUser,
                customer = toCustomer!!.name,
                phone = toCustomer.phone,
                email = toCustomer.emailId ?: "",
                gst = toCustomer.gst ?: "",
                address = toCustomer.getFormattedAddress() ?: "",
                service = services.map { it.toDto() },
                amount = amount,
                //paymentInstruction = paymentNote,
                //reference = reference,
                //notes = notes,
                //signature = signature,
                creationDate = creationDate,
                dueDate = dueDate,
                tax = tax,
                taxType = taxType
            )
        }

    }

    private fun validate(): Boolean = with(uiState.value) {
        if (invoiceNumber.isBlank()) {
            _uiState.update { it.copy(hasInvoiceNumberError = true) }
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

        if (services.isEmpty()) {
            _uiState.update { it.copy(hasServicesError = true) }
            return@with false
        }

        if (amount.isBlank()) {
            _uiState.update { it.copy(hasAmountError = true) }
            return@with false
        }

        if (dueDate.isBlank()) {
            _uiState.update { it.copy(hasDueDateError = true) }
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