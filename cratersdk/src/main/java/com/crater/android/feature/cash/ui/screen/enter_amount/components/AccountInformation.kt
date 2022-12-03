package com.crater.android.feature.cash.ui.screen.enter_amount.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun ColumnScope.AccountInformation(
    label: String,
    title: String,
    text: String
) {
    Text(
        text = label,
        fontWeight = FontWeight.SemiBold,
        fontSize = MaterialTheme.typography.body1.fontSize,
        color = MaterialTheme.typography.body2.color,
        modifier = Modifier.padding(
            start = AppTheme.dimensions.spacingExtraSmall,
            bottom = AppTheme.dimensions.spacingSmall
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = AppTheme.colors.divider,
                shape = AppTheme.shapes.medium
            )
            .padding(
                horizontal = AppTheme.dimensions.spacingSmall,
                vertical = AppTheme.dimensions.spacingExtraSmall,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_check_verified),
            contentDescription = null,
            tint = AppTheme.colors.primary
        )

        Column {
            Text(
                text = title,
                style = AppTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = text,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.textSecondary
            )
        }
    }
}