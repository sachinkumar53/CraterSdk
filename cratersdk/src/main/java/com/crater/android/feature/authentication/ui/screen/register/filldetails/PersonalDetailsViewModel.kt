package com.crater.android.feature.authentication.ui.screen.register.filldetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.common.model.Email
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.data.model.profile.UserName
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
class PersonalDetailsViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(errorEvent = triggered(msg), isLoading = false) }
    }

    fun onFirstNameChanged(value: String) {
        _uiState.update { it.copy(firstName = value, hasFirstNameError = false) }
    }

    fun onLastNameChanged(value: String) {
        _uiState.update { it.copy(lastName = value, hasLastNameError = false) }
    }

    fun onGenderChanged(value: String) {
        _uiState.update { it.copy(gender = value, hasGenderError = false) }
    }

    fun onDobChanged(value: String) {
        _uiState.update { it.copy(dob = value, hasDobError = false) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, hasEmailError = false) }
    }

    fun onCityChanged(value: String) {
        _uiState.update { it.copy(city = value, hasCityError = false) }
    }

    fun onStateChanged(value: String) {
        _uiState.update { it.copy(state = value, hasStateError = false) }
    }

    fun onPinCodeChanged(value: String) {
        if (value.length <= 6) {
            _uiState.update { it.copy(pinCode = value, hasPinCodeError = false) }
        }
    }

    fun onContinueClick() {
        if (!validate()) return
        submitDetails()
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    private fun validate(): Boolean {
        if (uiState.value.firstName.isBlank()) {
            _uiState.update { it.copy(hasFirstNameError = true) }
            return false
        }
        if (uiState.value.lastName.isBlank()) {
            _uiState.update { it.copy(hasLastNameError = true) }
            return false
        }
        if (uiState.value.gender.isBlank()) {
            _uiState.update { it.copy(hasGenderError = true) }
            return false
        }
        if (uiState.value.dob.isBlank()) {
            _uiState.update { it.copy(hasDobError = true) }
            return false
        }
        if (uiState.value.email.isBlank() || !Email(uiState.value.email).validate()) {
            _uiState.update { it.copy(hasEmailError = true) }
            return false
        }
        if (uiState.value.city.isBlank()) {
            _uiState.update { it.copy(hasCityError = true) }
            return false
        }
        if (uiState.value.state.isBlank()) {
            _uiState.update { it.copy(hasStateError = true) }
            return false
        }

        if (uiState.value.pinCode.isBlank() || uiState.value.pinCode.length < 6) {
            _uiState.update { it.copy(hasPinCodeError = true) }
            return false
        }

        return true
    }

    private fun submitDetails() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val userDetails = with(uiState.value) {
                UserDetails(
                    userName = UserName(firstName, lastName),
                    gender = gender,
                    dob = dob,
                    emailId = email,
                    city = city,
                    state = state,
                    pinCode = pinCode
                )
            }
            val response = repository.submitUserDetails(userDetails)
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }
    }
}