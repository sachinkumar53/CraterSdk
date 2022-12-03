package com.crater.android.feature.invoicing.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun EmptyListPlaceholder(
    modifier: Modifier = Modifier,
    text: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.no_transactions),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
        Text(text = text)
        Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraSmall))
        Text(
            text = description,
            style = AppTheme.typography.bodySmall,
            color = AppTheme.colors.textSecondary
        )
    }
}