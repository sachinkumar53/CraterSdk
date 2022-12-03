package com.crater.android.feature.invoicing.ui.screen.invoice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.invoicing.domain.model.InvoiceInfo
import com.crater.android.feature.invoicing.ui.common.EmptyListPlaceholder
import com.crater.android.feature.invoicing.ui.common.InvoiceTrackerCard
import com.crater.android.feature.invoicing.ui.screen.invoice.component.InvoiceListItem

@Composable
fun InvoiceListScreenContent(
    onCreateInvoiceButtonClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onInvoiceClick: (InvoiceInfo) -> Unit,
    uiState: InvoiceListUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        InvoiceTrackerCard(tracker = uiState.invoiceTracker)
        FancyButton(
            onClick = onCreateInvoiceButtonClick,
            text = stringResource(id = R.string.create_invoice),
            modifier = Modifier.fillMaxWidth()
        )
        FancyHeader(
            title = { Text(text = stringResource(id = R.string.recent_invoices)) },
            trailing = {
                Text(text = stringResource(id = R.string.see_all),
                    modifier = Modifier.clickable { onSeeAllClick() })
            }
        )

        val invoiceList = uiState.recentInvoiceList
        if (invoiceList.isEmpty()) {
            EmptyListPlaceholder(
                text = stringResource(id = R.string.no_invoices_yet),
                description = stringResource(id = R.string.start_creating_invoices_now),
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                items(invoiceList) {
                    InvoiceListItem(
                        invoice = it,
                        onItemClick = onInvoiceClick
                    )
                }
            }
        }
    }

    if (uiState.isLoading) LoadingScreen()
}


@Preview(showSystemUi = true)
@Composable
fun InvoiceListScreenContentPreview() {
    CraterTheme {
        InvoiceListScreenContent(
            onCreateInvoiceButtonClick = {},
            onSeeAllClick = {},
            uiState = InvoiceListUiState(
                recentInvoiceList = (1..5).map {
                    InvoiceInfo(
                        invoiceNo = "123",
                        customerName = "User $it",
                        amount = Amount(it * 70.0),
                        paymentStatus = "unpaid",
                        dueDate = "12/10/22"
                    )
                }
            ),
            onInvoiceClick = {}
        )
    }
}