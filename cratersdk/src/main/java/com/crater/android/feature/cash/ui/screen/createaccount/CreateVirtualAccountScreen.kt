package com.crater.android.feature.cash.ui.screen.createaccount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.cash.ui.model.UpiValidationStatus
import com.crater.android.feature.destinations.AccountDashboardScreenDestination
import com.crater.android.feature.destinations.AccountFeaturesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun CreateVirtualAccountScreen(
    viewModel: CreateAccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = { success ->
            if (success) {
                navigator.navigate(AccountDashboardScreenDestination()) {
                    popUpTo(AccountFeaturesScreenDestination) { inclusive = true }
                }
            }
        }
    )

    CreateVirtualAccountScreenContent(
        onBackClick = navigator::navigateUp,
        onCreateClick = viewModel::onCreateClick,
        onUpiIdChanged = viewModel::onUpiIdChanged,
        uiState = uiState
    )

}

data class CreateVirtualAccountUiState(
    val upiId: String = "",
    val hasUpiIdError: Boolean = false,
    val upiValidationStatus: UpiValidationStatus = UpiValidationStatus.None,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Boolean>