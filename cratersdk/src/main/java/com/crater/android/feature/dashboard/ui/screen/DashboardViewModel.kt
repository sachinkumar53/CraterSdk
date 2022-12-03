package com.crater.android.feature.dashboard.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.common.ApiExceptionHandler
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.feature.dashboard.domain.repository.DashboardRepository
import com.crater.android.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    cacheManager: CacheManager,
//    savedStateHandle: SavedStateHandle,
    private val repository: DashboardRepository
) : ViewModel() {

    //    val userNameStateFlow = savedStateHandle.getStateFlow("username", "")
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val msg = ApiExceptionHandler.extractErrorMessage(throwable)
            ?: BaseViewModel.FALLBACK_ERROR_MESSAGE

        _uiState.update { it.copy(isLoading = false, errorEvent = triggered(msg)) }
    }

    init {
        fetchUserDetails()

        cacheManager.getUserDetails().onEach { details ->
            _uiState.update { it.copy(isLoading = false, userDetails = details) }
        }.launchIn(viewModelScope)
    }


    private fun fetchUserDetails() {
        viewModelScope.launch(exceptionHandler) {
            _uiState.update { it.copy(isLoading = false) }
            repository.fetchUserDetails()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onErrorEventConsumed() {
        _uiState.update { it.copy(errorEvent = consumed()) }
    }
}