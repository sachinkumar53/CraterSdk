package com.crater.android.feature.social.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun DashboardAppBar(
    modifier: Modifier = Modifier,
    iconPainter: Painter,
    title: String,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .then(modifier)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_left_arrow_white),
                contentDescription = null
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingExtraSmall))
            Text(text = title, style = AppTheme.typography.title, fontWeight = FontWeight.SemiBold)
        }
    }
}


@Composable
fun DashboardSubAppBar(
    iconPainter: Painter,
    title: String,
    subtitle: String,
    onDisconnectClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = AppTheme.dimensions.spacingMedium)
            .clip(AppTheme.shapes.large)
            .background(AppTheme.colors.surface)
            .padding(
                horizontal = AppTheme.dimensions.spacingMedium,
                vertical = AppTheme.dimensions.spacingExtraSmall
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = iconPainter,
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingExtraSmall))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = AppTheme.typography.labelLarge
            )
            Text(
                text = subtitle,
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colors.textSecondary
            )
        }

        TextButton(onClick = onDisconnectClick) {
            Text(
                text = stringResource(id = R.string.disconnect),
                color = AppTheme.colors.error,
                style = AppTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
fun AppBarPreview() {
    DashboardAppBar(
        iconPainter = painterResource(id = R.drawable.ic_instagram),
        title = "Instagram"
    ) {}
}