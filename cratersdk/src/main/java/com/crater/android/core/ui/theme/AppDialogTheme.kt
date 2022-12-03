package com.crater.android.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun AppDialogTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides LocalColors.current.copy(
            background = AppTheme.colors.surfaceVariant,
            surface = Color(0xFF243931)
        )
    ) {
        content()
    }
}