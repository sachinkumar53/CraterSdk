package com.crater.android.feature.social.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.theme.AppTheme


@Composable
fun InformationBox(
    painter: Painter,
    text: String,
    data: String
) {
    FancyCard(
        backgroundColor = AppTheme.colors.highlight,
        border = BorderStroke(
            width = 1.dp,
            color = AppTheme.colors.divider
        ),
        shape = AppTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = AppTheme.colors.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingExtraSmall))
                Text(text = text, style = AppTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            Divider(color = AppTheme.colors.divider)
            Text(
                text = data,
                style = AppTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingSmall)
            )

        }
    }
}
