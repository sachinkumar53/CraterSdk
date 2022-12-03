package com.crater.android.feature.invoicing.ui.screen.tracker.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
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
class AllPaymentTrackerViewModel @Inject constructor(
    private val repository: InvoiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AllPaymentTrackerUiState())
    val uiState = _uiState.asStateFlow()


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE

        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }


    fun fetchPaymentTrackers() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val trackers = repository.getAllPaymentTrackers()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    paymentTrackers = trackers
                )
            }
        }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

}