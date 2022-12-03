package com.crater.android.feature.invoicing.ui.screen.invoice.component

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.crater.android.R
import com.crater.android.core.ui.FancyListItem
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.invoicing.domain.model.InvoiceInfo

@Composable
fun InvoiceListItem(
    invoice: InvoiceInfo,
    onItemClick: (InvoiceInfo) -> Unit
) {
    FancyListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_invoice),
                contentDescription = null
            )
        },
        text = {
            Text(
                text = buildAnnotatedString {
                    append(invoice.customerName)
                    withStyle(SpanStyle(fontSize = AppTheme.typography.bodySmall.fontSize)) {
                        append(" (${invoice.paymentStatus})")
                    }
                }
            )
        },
        secondaryText = { Text(invoice.invoiceNo) },
        trailingText = { Text(invoice.amount.displayValue) },
        secondaryTrailingText = {
            Text(stringResource(id = R.string.due_date_format, invoice.dueDate))
        },
        onClick = { onItemClick(invoice) }
    )
}
