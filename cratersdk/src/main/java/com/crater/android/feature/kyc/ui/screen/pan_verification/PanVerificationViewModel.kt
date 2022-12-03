package com.crater.android.feature.kyc.ui.screen.pan_verification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.feature.kyc.data.dto.error.PanVerificationError
import com.crater.android.feature.kyc.domain.model.PanNumber
import com.crater.android.feature.kyc.domain.repository.VerificationRepository
import com.crater.android.feature.kyc.ui.model.PanVerificationUiState
import com.crater.android.utils.BaseViewModel
import com.crater.android.utils.CraterParsers
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PanVerificationViewModel @Inject constructor(
    private val repository: VerificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PanVerificationUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg: String = try {
            if (throwable is HttpException) {
                val json = throwable.response()?.errorBody()?.string()
                val craterErrorResponse = CraterParsers.gson.fromJson(
                    json,
                    PanVerificationError::class.java
                )
                craterErrorResponse.detail.error.message
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }


    fun verifyPanNumber(onSuccess: () -> Unit) {
        val panNumber = uiState.value.panNumber
        if (panNumber == null || !panNumber.validate()) {
            _uiState.update { it.copy(isInvalidPanNumber = true) }
        } else {
            onSuccess()
            viewModelScope.launch(exceptionHandler) {
                _uiState.update { it.copy(isLoading = true) }
                val isSuccessful = repository.verifyPanNumber(panNumber)
                _uiState.update {
                    it.copy(
                        successEvent = triggered(isSuccessful),
                        isLoading = false
                    )
                }
            }
        }
    }


    fun onPanNumberChanged(value: String) {
        if (value.length <= PanNumber.PAN_NUMBER_LENGTH) {
            _uiState.update { it.copy(isInvalidPanNumber = false, panNumber = PanNumber(value)) }
        }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }
}