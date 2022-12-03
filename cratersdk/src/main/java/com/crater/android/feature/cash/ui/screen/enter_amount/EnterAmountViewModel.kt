package com.crater.android.feature.cash.ui.screen.enter_amount

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.cash.domain.repository.AccountRepository
import com.crater.android.feature.cash.domain.repository.TransactionRepository
import com.crater.android.feature.cash.ui.model.EnterAmountArgs
import com.crater.android.feature.cash.ui.model.FlowType
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
class EnterAmountViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val accountRepository: AccountRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update {
            it.copy(isLoading = false, errorEvent = triggered(msg))
        }
    }
    private val args: EnterAmountArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(EnterAmountUiState(userName = args.accountName))

    val uiState = _uiState.asStateFlow()

    init {
        if (args.flowType == FlowType.SEND_TO_SELF) {
            fetchUserBankAccountNumber()
        } else {
            _uiState.update { it.copy(accountNumber = args.accountId) }
        }
    }

    fun onAmountChanged(value: String) {
        _uiState.update { it.copy(amount = value, isAmountError = false) }
    }

    fun onRequestMoneyClick() {
        if (uiState.value.amount.isBlank()) {
            _uiState.update { it.copy(isAmountError = true) }
            return
        }

        requestMoney()
    }

    fun onSendMoneyClick() {
        if (uiState.value.amount.isBlank()) {
            _uiState.update { it.copy(isAmountError = true) }
            return
        }

        sendMoney()
    }

    private fun requestMoney() {
        if (args.flowType == FlowType.REQUEST_MONEY) {
            viewModelScope.launch(exceptionHandler) {
                _uiState.update { it.copy(isLoading = true) }
                val response = repository.requestPayment(
                    upiId = args.accountId,
                    amount = uiState.value.amount.toDouble()
                )
                _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
            }
        }
    }

    private fun sendMoney() {
        if (args.flowType == FlowType.SEND_TO_SELF) {
            viewModelScope.launch(exceptionHandler) {
                _uiState.update { it.copy(isLoading = true) }
                val response = repository.sendMoneyToSelf(amount = uiState.value.amount.toDouble())
                _uiState.update { it.copy(isLoading = false, successEvent = triggered(response)) }
            }
        }
    }

    private fun fetchUserBankAccountNumber() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = accountRepository.getUserBankAccountNumber()
            _uiState.update { it.copy(isLoading = false, accountNumber = "A/C No: $response") }
        }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }
}