package com.crater.android.feature.invoicing.ui.screen.tracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.destinations.AllPaymentTrackerScreenDestination
import com.crater.android.feature.destinations.CreatePaymentTrackScreenDestination
import com.crater.android.feature.destinations.PaymentTrackerPreviewScreenDestination
import com.crater.android.feature.invoicing.domain.model.PaymentTrackerInfo
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun PaymentTrackerScreen(
    viewModel: PaymentTrackerViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.fetchPaymentTrackerDetails() }

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    PaymentTrackerScreenContent(
        uiState = uiState,
        onCreateTrackerButtonClick = {
            navigator.navigate(CreatePaymentTrackScreenDestination())
        },
        onSeeAllClick = {
            navigator.navigate(AllPaymentTrackerScreenDestination())
        },
        onTrackerClick = {
            navigator.navigate(PaymentTrackerPreviewScreenDestination(it.id))
        }
    )
}


data class PaymentTrackerUiState(
    val tracker: PaymentTracker = PaymentTracker(0, Amount(0.0), Amount(0.0)),
    val recentTrackerList: List<PaymentTrackerInfo> = emptyList(),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Unit>