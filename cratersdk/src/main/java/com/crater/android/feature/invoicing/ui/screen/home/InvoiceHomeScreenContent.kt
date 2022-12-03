package com.crater.android.feature.invoicing.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.invoicing.ui.common.EmptyListPlaceholder
import com.crater.android.feature.invoicing.ui.common.InvoiceTrackerCard
import com.crater.android.feature.invoicing.ui.common.PaymentTrackerCard

@Composable
fun InvoiceHomeScreenContent(uiState: InvoiceHomeUiState) {

    Column(
        modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(
            AppTheme.dimensions.spacingMedium
        )
    ) {
        InvoiceTrackerCard(uiState.invoiceTracker)
        PaymentTrackerCard(uiState.paymentTracker)
        FancyHeader(title = { Text(text = stringResource(id = R.string.start_tracking)) })
        EmptyListPlaceholder(text = "", description = "", modifier = Modifier.weight(1f))
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun InvoiceHomeScreenContentPreview() {
    CraterTheme {
        InvoiceHomeScreenContent(uiState = InvoiceHomeUiState())
    }
}