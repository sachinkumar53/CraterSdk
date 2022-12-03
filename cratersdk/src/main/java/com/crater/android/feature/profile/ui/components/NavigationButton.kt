package com.crater.android.feature.profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(64.dp)
            .clip(AppTheme.shapes.medium)
            .background(AppTheme.colors.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimensions.spacingMedium,
                vertical = AppTheme.dimensions.spacingSmall
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                style = AppTheme.typography.titleSmall
            )
            if (description != null) {
                Text(
                    text = description,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colors.textSecondary
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_next),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun NavigationButtonPreview() {
    CraterTheme {
        NavigationButton(
            title = "Social Media",
            description = "Track all your social media stats"
        ) {}
    }
}
