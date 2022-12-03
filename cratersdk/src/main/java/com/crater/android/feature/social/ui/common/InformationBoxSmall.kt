package com.crater.android.feature.social.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.core.ui.FancyCard
import com.crater.android.core.ui.theme.AppTheme


@Composable
fun InformationBoxSmall(
    modifier: Modifier = Modifier,
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
        shape = AppTheme.shapes.medium,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .border(
                        width = 1.dp,
                        color = Color(0x33FFFFFF),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = AppTheme.colors.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            Text(text = text)
            Text(
                text = data,
                style = AppTheme.typography.headlineSmall,
                fontSize = 20.sp
            )
        }
    }
}
