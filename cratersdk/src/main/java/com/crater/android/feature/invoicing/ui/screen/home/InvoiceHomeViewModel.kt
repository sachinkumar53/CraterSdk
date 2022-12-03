package com.crater.android.feature.invoicing.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.invoicing.domain.model.Tracker
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoiceHomeViewModel @Inject constructor(
    private val repository: InvoiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InvoiceHomeUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update {
            it.copy(isLoading = false, errorEvent = triggered(msg))
        }
    }

    fun fetchTrackers() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }

            val (invoiceTracker, paymentTracker) = listOf(
                async { repository.getInvoiceTracker() },
                async { repository.getPaymentTracker() },
            ).awaitAll()

            _uiState.update {
                it.copy(
                    isLoading = false,
                    invoiceTracker = invoiceTracker as InvoiceTracker,
                    paymentTracker = paymentTracker as PaymentTracker
                )
            }
        }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }
}