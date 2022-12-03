package com.crater.android.feature.invoicing.ui.screen.createinvoice.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.crater.android.R
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.Service

@Composable
fun Table(
    services: List<Service>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.subtitle2,
            LocalContentAlpha provides ContentAlpha.medium
        ) {
            Row(modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingTiny)) {
                Text(
                    text = stringResource(id = R.string.service_name),
                    modifier = Modifier.weight(0.6f)
                )
                Text(
                    text = stringResource(id = R.string.qty),
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.amount),
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
            }
        }
        Divider()
        services.forEach {
            Row(modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingTiny)) {
                Text(
                    text = it.name,
                    modifier = Modifier.weight(0.6f)
                )
                Text(
                    text = it.quantity.toString(),
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = it.price,
                    modifier = Modifier.weight(0.2f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
