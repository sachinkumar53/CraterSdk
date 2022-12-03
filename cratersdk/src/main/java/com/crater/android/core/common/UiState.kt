package com.crater.android.core.common

import de.palm.composestateevents.StateEventWithContent

interface UiState<T> {
    val isLoading: Boolean
    val successEvent: StateEventWithContent<T>
    val errorEvent: StateEventWithContent<String>
}