package com.crater.android.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun FancyHeader(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    topMargin: Dp = AppTheme.dimensions.spacingSmall
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = topMargin),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            val titleTextStyle = AppTheme.typography.headlineSmall
            val subtitleTextStyle = AppTheme.typography.bodySmall.copy(
                color = AppTheme.colors.textSecondary
            )

            CompositionLocalProvider(LocalTextStyle provides titleTextStyle) {
                title()
            }

            if (subtitle != null) {
                CompositionLocalProvider(LocalTextStyle provides subtitleTextStyle) {
                    subtitle()
                }
            }
        }

        if (trailing != null) {
            CompositionLocalProvider(
                LocalTextStyle provides AppTheme.typography.labelMedium.copy(
                    color = AppTheme.colors.textSecondary
                )
            ) {
                trailing()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    CraterTheme {
        FancyHeader(
            title = {
                Text(text = "Sample heading")
            },
            subtitle = {
                Text(text = "Subtitle")
            },
            trailing = {
                Text(text = "See all")
            }
        )
    }
}