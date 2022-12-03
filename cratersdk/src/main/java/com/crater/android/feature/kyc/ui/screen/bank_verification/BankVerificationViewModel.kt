package com.crater.android.feature.kyc.ui.screen.bank_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.common.model.Email
import com.crater.android.core.common.model.Name
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.feature.kyc.domain.model.BankDetails
import com.crater.android.feature.kyc.domain.repository.VerificationRepository
import com.crater.android.feature.kyc.ui.model.AccountNumber
import com.crater.android.feature.kyc.ui.model.BankVerificationUiState
import com.crater.android.feature.kyc.ui.model.IFSC
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankVerificationViewModel @Inject constructor(
    private val repository: VerificationRepository,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BankVerificationUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val error = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE

        _uiState.update {
            it.copy(
                errorEvent = triggered(error),
                isLoading = false
            )
        }
    }

    fun onNameChanged(value: String) {
        _uiState.update {
            it.copy(
                accountName = Name(value),
                isNameError = false
            )
        }
    }

    fun onAccountNumberChanged(value: String) {
        _uiState.update {
            it.copy(
                accountNumber = AccountNumber(value),
                isAccountNumberError = false
            )
        }
    }

    fun onIFSCCodeChanged(value: String) {
        if (value.length <= IFSC.IFSC_CODE_LENGTH) {
            _uiState.update {
                it.copy(
                    ifsc = IFSC(value),
                    isIfscCodeError = false
                )
            }
        }
    }

    fun onEmailChanged(value: String) {
        _uiState.update {
            it.copy(
                email = Email(value),
                isEmailError = false
            )
        }
    }


    fun onContinueClicked() {
        if (validate()) {
            verifyBankAccount()
        }
    }

    private fun validate(): Boolean {
        return with(_uiState.value) {
            if (!accountName.validate()) {
                _uiState.value = copy(isNameError = true)
                return@with false
            }

            if (!accountNumber.validate()) {
                _uiState.value = copy(isAccountNumberError = true)
                return@with false
            }

            if (!ifsc.validate()) {
                _uiState.value = copy(isIfscCodeError = true)
                return@with false
            }

            if (!email.validate()) {
                _uiState.value = copy(isEmailError = true)
                return@with false
            }
            true
        }
    }

    private fun verifyBankAccount() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val isSuccess = repository.verifyBankDetails(
                BankDetails(
                    accountName = uiState.value.accountName,
                    accountNumber = uiState.value.accountNumber,
                    ifsc = uiState.value.ifsc,
                    email = uiState.value.email
                )
            )
            if (isSuccess) {
                val currentUserDetails = cacheManager.getUserDetails().first()
                cacheManager.setUserDetails(currentUserDetails.copy(isBankKycDone = true))
            }
            _uiState.update { it.copy(isLoading = false, successEvent = triggered(isSuccess)) }
        }
    }

    fun onErrorEventConsumed() {
        _uiState.update {
            it.copy(errorEvent = consumed())
        }
    }

    fun onSuccessEventConsumed() {
        _uiState.update {
            it.copy(successEvent = consumed())
        }
    }

}