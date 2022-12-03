package com.crater.android.feature.authentication.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.common.UiState
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE

        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }


    fun onContinueClick() {
        if (!validate()) return
        login()
    }

    fun onPhoneChanged(value: String) {
        _uiState.update { it.copy(hasPhoneNumber = false, phoneNumber = value) }
    }

    private fun validate(): Boolean {
        if (uiState.value.phoneNumber.length != 10) {
            _uiState.update { it.copy(hasPhoneNumber = true) }
            return false
        }

        return true
    }

    private fun login() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.login(uiState.value.phoneNumber)
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


data class LoginUiState(
    val phoneNumber: String = "",
    val hasPhoneNumber: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Boolean>