package com.crater.android.feature.authentication.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
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
class RegisterViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(errorEvent = triggered(msg), isLoading = false) }
    }


    fun onPhoneNumberChanged(value: String) {
        if (value.length <= 10) {
            _uiState.update { it.copy(phoneNumber = value, hasPhoneNumberError = false) }
        }
    }

    fun onInviteCodeChanged(value: String) {
        _uiState.update { it.copy(inviteCode = value) }
    }


    fun onTermsAndConditionCheckChange(value: Boolean) {
        _uiState.update { it.copy(isTermsAndConditionChecked = value) }
    }

    fun onContinueClick() {
        if (!validate()) return
        registerUser()
    }

    private fun validate(): Boolean {
        if (uiState.value.phoneNumber.length != 10) {
            _uiState.update { it.copy(hasPhoneNumberError = true) }
            return false
        }
        if (!uiState.value.isTermsAndConditionChecked) {
            _uiState.update { it.copy(hasTermsAndConditionError = true) }
            return false
        }

        return true
    }

    private fun registerUser() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val (phone, referral) = with(uiState.value) { phoneNumber to inviteCode }
            val response = repository.registerUser(phone, referral)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }
}