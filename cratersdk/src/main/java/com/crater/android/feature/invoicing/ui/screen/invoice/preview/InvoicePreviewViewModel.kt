package com.crater.android.feature.invoicing.ui.screen.invoice.preview

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
import com.crater.android.feature.invoicing.ui.model.PreviewArgs
import com.crater.android.feature.navArgs
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvoicePreviewViewModel @Inject constructor(
    private val repository: InvoiceRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args: PreviewArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(InvoicePreviewUiState())
    val uiState = _uiState.asStateFlow()


    private val mainExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg: String = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, resource = Resource.Error(msg)) }
        Log.e("Hello", "loadInvoicePreviewDetails: ", throwable)
    }

    private val optionExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg: String = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(errorEvent = triggered(msg), isLoading = false) }
    }

    init {
        loadInvoicePreviewDetails()
    }

    private fun loadInvoicePreviewDetails() {
        viewModelScope.launch(mainExceptionHandler) {
            _uiState.update { it.copy(resource = Resource.Loading()) }
            val details = repository.getInvoicePreviewDetails(args.invoiceNumber)
            _uiState.update { it.copy(resource = Resource.Success(details)) }
        }
    }

    fun generateUpiLink(then: () -> Unit) {
        viewModelScope.launch(optionExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.generatePaymentLink(args.invoiceNumber)
            _uiState.update { it.copy(isLoading = false, paymentInfo = response) }
            //Wait a few millis to let the QR code to be drawn
            delay(100)
            then()
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch(optionExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.deleteInvoice(args.invoiceNumber)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }
    }

    fun onDeleteSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

}