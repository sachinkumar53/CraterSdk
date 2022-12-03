package com.crater.android.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class AppDimensions(
    val spacingTiny: Dp = 4.dp,
    val spacingExtraSmall: Dp = 8.dp,
    val spacingSmall: Dp = 12.dp,
    val spacingMedium: Dp = 16.dp,
    val spacingLarge: Dp = 24.dp,
    val spacingExtraLarge: Dp = 32.dp,
    val iconExtraSmall: Dp = 16.dp,
    val iconSmall: Dp = 20.dp,
    val iconDefault: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,
    val dividerHeight: Dp = 1.dp,
    val appBarHeight: Dp = 56.dp,
    val minButtonHeight: Dp = 48.dp,
    val minButtonSmall: Dp = 44.dp
)

internal val LocalDimensions = staticCompositionLocalOf { AppDimensions() }