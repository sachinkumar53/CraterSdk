package com.crater.android.feature.invoicing.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.gradientCard
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.Tracker

@Composable
fun InvoiceTrackerCard(tracker: Tracker.InvoiceTracker) {
    TrackerCard(
        title = stringResource(id = R.string.build_invoice),
        description = stringResource(id = R.string.build_invoice_desc),
        label = stringResource(id = R.string.invoices),
        tracker = tracker.asString(),
        amountDue = tracker.amountDue.displayValue,
        iconPainter = painterResource(id = R.drawable.ic_invoice)
    )
}


@Composable
fun PaymentTrackerCard(tracker: Tracker.PaymentTracker) {
    TrackerCard(
        title = stringResource(id = R.string.track_payment),
        description = stringResource(id = R.string.track_payments_desc),
        label = stringResource(id = R.string.payment_tracker),
        tracker = tracker.asString(),
        amountDue = tracker.amountDue.displayValue,
        iconPainter = painterResource(id = R.drawable.ic_tracker)
    )
}

@Composable
private fun TrackerCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    label: String,
    tracker: String,
    amountDue: String,
    iconPainter: Painter
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .gradientCard()
            .padding(AppTheme.dimensions.spacingMedium)
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.height(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                contentScale = ContentScale.FillHeight
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingTiny)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = description,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.textSecondary
                )
            }
        }

        Divider()

        Row {
            SummaryInfo(
                label = label,
                info = tracker,
                modifier = Modifier.weight(0.6f)
            )
            SummaryInfo(
                label = stringResource(id = R.string.amount_due),
                info = amountDue,
                modifier = Modifier.weight(0.4f)
            )
        }
    }
}

@Composable
private fun SummaryInfo(
    modifier: Modifier = Modifier, label: String, info: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingTiny)
    ) {
        Text(
            text = label,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary
        )
        Text(
            text = info,
            style = AppTheme.typography.labelLarge
        )
    }
}