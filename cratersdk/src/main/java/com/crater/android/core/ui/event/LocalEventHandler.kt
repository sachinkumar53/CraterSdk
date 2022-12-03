package com.crater.android.core.ui.event

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

val LocalEventHandler: ProvidableCompositionLocal<EventHandler?> = compositionLocalOf { null }