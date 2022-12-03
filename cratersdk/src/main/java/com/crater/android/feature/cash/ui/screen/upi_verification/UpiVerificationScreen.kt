package com.crater.android.feature.cash.ui.screen.upi_verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.EnterAmountScreenDestination
import com.crater.android.feature.destinations.UpiVerificationScreenDestination
import com.crater.android.feature.cash.ui.model.EnterAmountArgs
import com.crater.android.feature.cash.ui.model.FlowType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun UpiVerificationScreen(
    viewModel: UpiVerificationViewModel = hiltViewModel(),
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
        action = { name ->
            context.showToast(R.string.upi_verification_successful)
            navigator.navigate(
                EnterAmountScreenDestination(
                    EnterAmountArgs(
                        accountName = name,
                        accountId = uiState.upiId,
                        flowType = FlowType.REQUEST_MONEY
                    )
                )
            ) {
                popUpTo(UpiVerificationScreenDestination) {
                    inclusive = true
                }
            }
        }
    )

    UpiVerificationScreenContent(
        onBackClick = navigator::navigateUp,
        onUpiIdChanged = viewModel::onUpiIdChanged,
        onContinueClick = viewModel::onContinueClick,
        uiState = uiState
    )
}

data class UpiVerificationUiState(
    val upiId: String = "",
    val isUpiIdError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<String>
