package com.crater.android.feature.cash.ui.screen.createaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.cash.domain.repository.AccountRepository
import com.crater.android.feature.cash.ui.model.UpiValidationStatus
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateVirtualAccountUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }

    private var validationJob: Job? = null

    fun onUpiIdChanged(id: String) {
        if (id.length <= 18) {
            _uiState.update { it.copy(hasUpiIdError = false, upiId = id) }
            _uiState.update { it.copy(upiValidationStatus = UpiValidationStatus.None) }
            validationJob?.cancel()
            if (isValidUpi(id)) {
                validationJob = validateUpi()
            }
        }
    }

    fun onCreateClick() {
        if (!validate()) return
        createVirtualAccount()
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }


    private fun validateUpi() = viewModelScope.launch(exceptionHandler) {
        delay(800L)
        _uiState.update { it.copy(upiValidationStatus = UpiValidationStatus.Verifying) }
        val response = repository.validateUpi(uiState.value.upiId).getOrDefault(false)
        _uiState.update {
            it.copy(
                upiValidationStatus = if (response) {
                    UpiValidationStatus.Available
                } else {
                    UpiValidationStatus.Unavailable
                }
            )
        }

    }

    private fun createVirtualAccount() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.createVirtualAccountWithCustomUpiId(uiState.value.upiId)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }
    }

    private fun validate(): Boolean {
        val upiId = uiState.value.upiId
        if (!isValidUpi(upiId)) {
            _uiState.update { it.copy(hasUpiIdError = true) }
            return false
        }
        return true
    }

    private fun isValidUpi(upiId: String): Boolean {
        return upiId.isNotBlank() && upiId.all { it.isLetterOrDigit() }
    }

}