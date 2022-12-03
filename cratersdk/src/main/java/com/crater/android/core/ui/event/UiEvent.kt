package com.crater.android.core.ui.event

import com.crater.android.core.util.UiText

sealed interface UiEvent {
    class ShowToast(val text: UiText) : UiEvent
}