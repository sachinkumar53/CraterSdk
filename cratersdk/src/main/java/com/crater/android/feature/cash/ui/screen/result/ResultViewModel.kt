package com.crater.android.feature.cash.ui.screen.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.feature.cash.domain.repository.TransactionRepository
import com.crater.android.feature.cash.ui.model.FlowType
import com.crater.android.feature.cash.ui.model.ResultSuccessArgs
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
class ResultViewModel @Inject constructor(
    private val repository: TransactionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val args: ResultSuccessArgs = savedStateHandle.navArgs()

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update {
            it.copy(isLoading = false, errorEvent = triggered(msg))
        }
    }

    private var generatedLink: String? = null

    fun onShareClick() {
        if (args.flowType == FlowType.REQUEST_MONEY) {
            val link = generatedLink
            if (link != null) {
                _uiState.update { it.copy(successEvent = triggered(link)) }
                return
            }
            generatePaymentLink(args.amount.value)
        }
    }

    private fun generatePaymentLink(amount: Double) {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val response = repository.generatePaymentLink(amount)
            generatedLink = response
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