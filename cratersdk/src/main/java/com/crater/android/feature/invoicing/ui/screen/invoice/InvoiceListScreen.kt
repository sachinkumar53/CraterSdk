package com.crater.android.feature.invoicing.ui.screen.invoice

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.AllInvoiceScreenDestination
import com.crater.android.feature.destinations.CreateInvoiceScreenDestination
import com.crater.android.feature.destinations.InvoicePreviewScreenDestination
import com.crater.android.feature.invoicing.domain.model.InvoiceInfo
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.cash.domain.model.Amount
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun InvoiceListScreen(
    viewModel: InvoiceListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit){ viewModel.fetchInvoiceDetails() }

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    InvoiceListScreenContent(
        uiState = uiState,
        onCreateInvoiceButtonClick = {
            navigator.navigate(CreateInvoiceScreenDestination())
        },
        onSeeAllClick = {
            navigator.navigate(AllInvoiceScreenDestination())
        },
        onInvoiceClick = {
            navigator.navigate(InvoicePreviewScreenDestination(it.invoiceNo))
        }
    )
}

data class InvoiceListUiState(
    val invoiceTracker: InvoiceTracker = InvoiceTracker(0, Amount(0.0), Amount(0.0)),
    val recentInvoiceList: List<InvoiceInfo> = emptyList(),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Unit>