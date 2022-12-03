package com.crater.android.feature.dashboard.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.gradientCard
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun ServiceCard(
    onClick: () -> Unit,
    iconPainter: Painter,
    title: String,
    description: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .heightIn(96.dp)
            .gradientCard()
            .clickable {
                onClick()
            }
            .padding(AppTheme.dimensions.spacingMedium),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = AppTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingTiny))
            Text(
                text = description,
                color = AppTheme.colors.textSecondary
            )
        }

        Image(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.height(AppTheme.dimensions.iconLarge),
            contentScale = ContentScale.FillHeight,
            colorFilter = ColorFilter.tint(AppTheme.colors.primary)
        )
    }
}

