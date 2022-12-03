package com.crater.android.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {

    var isLoadingDetails = true
        private set

    private val _userDetails = MutableStateFlow(UserDetails())
    val userDetails = _userDetails.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }

    init {
        fetchUserDetails()
    }


    private fun fetchUserDetails() {
        viewModelScope.launch(exceptionHandler) {
            isLoadingDetails = true
            _accessToken.update { repository.getAccessToken() }
            if (accessToken.value.isNullOrBlank()) {
                isLoadingDetails = false
            } else {
                _userDetails.update { repository.getUserDetails() }
                delay(200)
                isLoadingDetails = false
            }
        }
    }

}