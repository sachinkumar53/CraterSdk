package com.crater.android.feature.invoicing.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun BoxButton(
    onClick: () -> Unit,
    iconPainter: Painter,
    text: String
) {
    Column(
        modifier = Modifier
            .padding(vertical = AppTheme.dimensions.spacingSmall)
            .clip(AppTheme.shapes.large)
            .background(AppTheme.colors.highlight)
            .border(
                width = 1.dp,
                color = AppTheme.colors.divider,
                shape = AppTheme.shapes.large
            )
            .clickable { onClick() }
            .padding(AppTheme.dimensions.spacingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = colorResource(id = R.color.white_text)
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
        Text(
            text = text,
            color = colorResource(id = R.color.white_text),
            fontSize = 13.sp
        )
    }
}