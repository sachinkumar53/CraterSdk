package com.crater.android.core.ui.event

import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.Flow

interface EventHandler {
    val event: Flow<UiEvent>

    suspend fun sendEvent(event: UiEvent)

    fun attachTo(activity: ComponentActivity)
}