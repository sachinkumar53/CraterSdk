package com.crater.android.feature.invoicing.ui.screen.invoice.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.crater.android.core.ui.theme.AppTheme

@Composable
fun InvoiceHeader(
    modifier: Modifier = Modifier,
    invoiceNumber: String,
    datePrefix: String,
    date: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(AppTheme.dimensions.spacingMedium)
            .then(modifier),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentColor provides AppTheme.colors.onPrimary) {
            Text(
                text = invoiceNumber,
                style = AppTheme.typography.headlineSmall
            )
            Text(
                text = buildAnnotatedString {
                    append(datePrefix)
                    append(": ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(date)
                    }
                },
                style = AppTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}