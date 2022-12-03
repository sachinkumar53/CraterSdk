package com.crater.android.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun QuickAccessMenuItem(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),

        color = AppTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary.copy(alpha = 0.2f))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.size(width = 88.dp, height = 100.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = text,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
            Text(
                text = text,
                fontSize = AppTheme.typography.bodySmall.fontSize,
                maxLines = 2,
                modifier = Modifier.padding(horizontal = AppTheme.dimensions.spacingSmall),
                textAlign = TextAlign.Center
            )
        }
    }
}