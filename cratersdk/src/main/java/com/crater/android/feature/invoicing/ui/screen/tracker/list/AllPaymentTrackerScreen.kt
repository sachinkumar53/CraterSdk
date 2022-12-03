package com.crater.android.feature.invoicing.ui.screen.tracker.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.PaymentTrackerPreviewScreenDestination
import com.crater.android.feature.invoicing.domain.model.PaymentTrackerInfo
import com.crater.android.feature.invoicing.ui.screen.tracker.component.PaymentTrackerListItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun AllPaymentTrackerScreen(
    viewModel: AllPaymentTrackerViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { viewModel.fetchPaymentTrackers() }

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.all_tracking_record),
                onBackClick = navigator::navigateUp
            )
        }
    ) { padding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
            modifier = Modifier
                .padding(padding)
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            items(uiState.paymentTrackers) {
                PaymentTrackerListItem(
                    info = it,
                    onItemClick = {
                        navigator.navigate(PaymentTrackerPreviewScreenDestination(it.id))
                    }
                )
            }
        }
    }
    if (uiState.isLoading) LoadingScreen()
}


data class AllPaymentTrackerUiState(
    val paymentTrackers: List<PaymentTrackerInfo> = emptyList(),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Unit>