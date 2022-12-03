package com.crater.android.feature.invoicing.ui.screen.tracker.preview

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerPreviewViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: InvoiceRepository
) : ViewModel() {
    private val args: PreviewArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(TrackerPreviewUiState())
    val uiState = _uiState.asStateFlow()


    private val mainExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg: String = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(resource = Resource.Error(msg)) }
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
            val details = repository.getTrackerPreviewDetails(args.invoiceNumber)
            _uiState.update { it.copy(resource = Resource.Success(details)) }
        }
    }

    fun onDeleteClick() {
        viewModelScope.launch(optionExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.deletePaymentTracker(args.invoiceNumber)
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