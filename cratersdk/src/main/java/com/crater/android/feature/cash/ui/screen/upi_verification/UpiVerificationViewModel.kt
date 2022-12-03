package com.crater.android.feature.cash.ui.screen.upi_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.cash.domain.repository.TransactionRepository
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
class UpiVerificationViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UpiVerificationUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update {
            it.copy(
                isLoading = false,
                errorEvent = triggered(msg)
            )
        }
    }

    fun onUpiIdChanged(value: String) {
        _uiState.update { it.copy(upiId = value, isUpiIdError = false) }
    }

    fun onContinueClick() {
        if (validate()) {
            verifyUpiId(uiState.value.upiId)
        }
    }

    private fun verifyUpiId(upiId: String) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val result = repository.verifyUpiId(upiId)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    successEvent = triggered(result.name)
                )
            }
        }
    }

    private fun validate(): Boolean {
        val upiId = uiState.value.upiId
        if (upiId.isBlank()) {
            _uiState.update { it.copy(isUpiIdError = true) }
            return false
        }

        val pattern = UPI_ID_PATTERN.toPattern().matcher(upiId)
        if (!pattern.matches()) {
            _uiState.update { it.copy(isUpiIdError = true) }
            return false
        }
        return true
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    companion object {
        private const val UPI_ID_PATTERN = "[a-zA-Z0-9.\\-_]{2,256}@[a-zA-Z]{2,64}"
    }
}