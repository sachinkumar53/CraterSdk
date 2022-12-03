package com.crater.android.core.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

inline fun <T> LifecycleOwner.observeFlow(
    flow: Flow<T>,
    crossinline action: (T) -> Unit,
) {
    flow.onEach {
        action(it)
    }.launchIn(lifecycleScope)
}