package com.crater.android.core.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme

@Composable
@NonRestartableComposable
fun FancyCard(
    modifier: Modifier = Modifier,
    shape: Shape = AppTheme.shapes.default,
    backgroundColor: Color = AppTheme.colors.surfaceVariant,
    contentColor: Color = LocalContentColor.current,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        border = border,
        content = content
    )

}