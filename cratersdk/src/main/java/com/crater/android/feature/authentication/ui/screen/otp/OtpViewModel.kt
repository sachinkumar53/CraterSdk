package com.crater.android.feature.authentication.ui.screen.otp

import android.os.CountDownTimer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
import com.crater.android.feature.authentication.ui.model.OtpScreenArgs
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
class OtpViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val args: OtpScreenArgs = savedStateHandle.navArgs()
    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(errorEvent = triggered(msg), isLoading = false) }
    }

    private val countDownTimer = object : CountDownTimer(OTP_RESEND_DELAY, 1000) {
        override fun onTick(millis: Long) {
            _uiState.update { it.copy(remainingTimeToResendOtp = (millis / 1000).toInt()) }
        }

        override fun onFinish() {

        }

    }

    init {
        countDownTimer.start()
    }

    fun onOtpChanged(value: String) {
        if (value.length <= OTP_LENGTH) {
            _uiState.update { it.copy(otp = value, hasOtpError = false) }
        }
    }

    fun onVerifyClick() {
        if (!validate()) return
        verifyOtp()
    }

    fun onResendClick() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            repository.resendOtp(phone = args.phoneNumber)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    errorEvent = triggered("OTP sent successfully!")
                )
            }
            countDownTimer.start()
        }
    }

    private fun validate(): Boolean {
        if (uiState.value.otp.length != OTP_LENGTH) {
            _uiState.update { it.copy(hasOtpError = true) }
            return false
        }

        return true
    }

    private fun verifyOtp() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.verifyOtp(
                flowType = args.flowType,
                phone = args.phoneNumber,
                otp = uiState.value.otp,
                inviteCode = args.inviteCode
            )
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
        }
    }


    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }


    companion object {
        private const val OTP_RESEND_DELAY = 60 * 1000L // FIXME: Change to 120
        const val OTP_LENGTH = 6
    }
}