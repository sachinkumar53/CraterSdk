package com.crater.android.feature.invoicing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun MenuButton(
    onClick: () -> Unit,
    iconPainter: Painter,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(AppTheme.shapes.medium)
            .fillMaxHeight()
            .background(AppTheme.colors.surfaceVariant)
            .border(
                width = 1.dp,
                color = AppTheme.colors.divider,
                shape = AppTheme.shapes.medium
            )
            .clickable { onClick() }
            .padding(
                horizontal = AppTheme.dimensions.spacingSmall,
                vertical = AppTheme.dimensions.spacingMedium
            ),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
        Text(
            text = label,
            textAlign = TextAlign.Center,
            style = AppTheme.typography.bodySmall
        )
    }
}