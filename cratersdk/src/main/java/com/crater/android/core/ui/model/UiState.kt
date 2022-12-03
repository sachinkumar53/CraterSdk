package com.crater.android.core.ui.model

import com.crater.android.core.util.UiText

sealed interface UiState<T> {
    class Loading<T>() : UiState<T>
    data class Success<T>(val data: T) : UiState<T>
    data class Error<T>(val msg: UiText? = null) : UiState<T>
    class Empty<T> : UiState<T>
}