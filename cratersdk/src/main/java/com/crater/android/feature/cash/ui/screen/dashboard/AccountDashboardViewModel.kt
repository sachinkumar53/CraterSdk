package com.crater.android.feature.cash.ui.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.feature.cash.domain.repository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountDashboardViewModel @Inject constructor(
    private val repository: AccountRepository,
    cacheManager: CacheManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AccountDashboardUiState())

    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        _uiState.update { it.copy(isLoading = false) }
    }

    init {
        fetchBankDetails()
        cacheManager.getUserDetails().onEach { details ->
            _uiState.update { it.copy(userName = details.userName) }
        }.launchIn(viewModelScope)
    }

    fun fetchBankDetails() {
        viewModelScope.launch(exceptionHandler + SupervisorJob()) {
            _uiState.update { it.copy(isLoading = true) }
            val accountDetailDeferred = async { repository.getAccountDetail() }
            val transactions = async { repository.getStatement() }
            val accountDetail = accountDetailDeferred.await()
            _uiState.update {
                it.copy(
                    accountDetail = accountDetail,
                    transactions = transactions.await(),
                    isLoading = false
                )
            }
        }
    }


}