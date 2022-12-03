package com.crater.android.core.ui.event

import androidx.activity.ComponentActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.crater.android.core.extension.showToast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventHandlerImpl @Inject constructor() : EventHandler {
    private val _eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    override val event: Flow<UiEvent> = _eventChannel.receiveAsFlow()

    override suspend fun sendEvent(event: UiEvent) {
        _eventChannel.send(event)
    }

    override fun attachTo(activity: ComponentActivity) {
        activity.run {
            lifecycleScope.launch {
                event.flowWithLifecycle(lifecycle).onEach { event ->
                    when (event) {
                        is UiEvent.ShowToast -> showToast(event.text.asString(this@run))
                    }
                }.launchIn(this)
            }
        }
    }
}