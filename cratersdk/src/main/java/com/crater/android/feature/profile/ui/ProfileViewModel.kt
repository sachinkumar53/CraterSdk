package com.crater.android.feature.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.db.AppDatabase
import com.crater.android.feature.profile.domain.repository.ProfileRepository
import com.crater.android.feature.profile.ui.screen.ProfileUiState
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val db: AppDatabase,
    private val cacheManager: CacheManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val errorMessage = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE
        _uiState.update {
            it.copy(
                errorEvent = triggered(errorMessage),
                isLoading = false
            )
        }
    }

    init {
        repository.getProfileDetails().onEach { userDetails ->
            _uiState.update {
                it.copy(
                    userName = userDetails.userName.name,
                    mobileNumber = userDetails.mobileNumber,
                    emailId = userDetails.emailId,
                    isAccountVerified = !userDetails.pan.isNullOrBlank()
                )
            }
        }.launchIn(viewModelScope)
    }

    fun logout() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            val message = repository.logout()
            clearCache()
            _uiState.update {
                it.copy(
                    isLoading = false,
                    successEvent = triggered(message)
                )
            }
        }
    }

    private suspend fun clearCache() {
        db.customerDao.clearCustomers()
        db.serviceDao.clearServices()
        cacheManager.clearCache()
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }

    fun onSuccessEventConsumed() {
        _uiState.update { it.copy(successEvent = consumed()) }
    }
}