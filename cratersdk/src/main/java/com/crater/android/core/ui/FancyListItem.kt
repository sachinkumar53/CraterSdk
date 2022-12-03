package com.crater.android.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun FancyListItem(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    text: @Composable () -> Unit,
    secondaryText: (@Composable () -> Unit)? = null,
    trailingText: (@Composable () -> Unit)? = null,
    secondaryTrailingText: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
) {

    Row(
        modifier = modifier
            .heightIn(56.dp)
            .background(
                color = AppTheme.colors.surfaceVariant,
                shape = AppTheme.shapes.large
            )
            .run {
                if (onClick != null)
                    clickable { onClick() }
                else this
            }
            .padding(
                horizontal = AppTheme.dimensions.spacingMedium,
                vertical = AppTheme.dimensions.spacingSmall,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(
                modifier = modifier
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = CircleShape
                    )
                    .size(AppTheme.dimensions.iconLarge)
                    .padding(AppTheme.dimensions.spacingExtraSmall),
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalContentColor provides AppTheme.colors.onPrimary) {
                    icon()
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(start = AppTheme.dimensions.spacingMedium)
                .weight(1f)
        ) {
            ProvideTextStyle(AppTheme.typography.labelLarge) {
                text()
            }
            if (secondaryText != null) {
                ProvideTextStyle(
                    AppTheme.typography.labelMedium.copy(color = AppTheme.colors.textSecondary)
                ) {
                    secondaryText()
                }
            }
        }

        if (trailingText != null || secondaryTrailingText != null) {
            Column(horizontalAlignment = Alignment.End) {
                if (trailingText != null) {
                    ProvideTextStyle(
                        AppTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    ) {
                        trailingText()
                    }
                }

                if (secondaryTrailingText != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    ProvideTextStyle(
                        AppTheme.typography.labelSmall.copy(color = AppTheme.colors.textSecondary)
                    ) {
                        secondaryTrailingText()
                    }
                }
            }
        }
    }
}