package com.crater.android.feature.invoicing.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker
import com.ramcosta.composedestinations.annotation.Destination
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun InvoiceHomeScreen(viewModel: InvoiceHomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.fetchTrackers() }

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    InvoiceHomeScreenContent(uiState = uiState)
}


data class InvoiceHomeUiState(
    val invoiceTracker: InvoiceTracker = InvoiceTracker(0, Amount(0.0), Amount(0.0)),
    val paymentTracker: PaymentTracker = PaymentTracker(0, Amount(0.0), Amount(0.0)),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Unit>