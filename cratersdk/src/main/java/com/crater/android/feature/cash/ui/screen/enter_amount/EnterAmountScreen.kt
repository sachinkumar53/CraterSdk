package com.crater.android.feature.cash.ui.screen.enter_amount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.EnterAmountScreenDestination
import com.crater.android.feature.destinations.RequestSuccessfulScreenDestination
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.ui.model.EnterAmountArgs
import com.crater.android.feature.cash.ui.model.ResultSuccessArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination(navArgsDelegate = EnterAmountArgs::class)
@Composable
fun EnterAmountScreen(
    viewModel: EnterAmountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    args: EnterAmountArgs
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
        action = {
            if (!it) return@EventEffect
            navigator.navigate(
                RequestSuccessfulScreenDestination(
                    ResultSuccessArgs(
                        amount = Amount(uiState.amount.toDouble()),
                        flowType = args.flowType
                    )
                )
            ) {
                popUpTo(EnterAmountScreenDestination) { inclusive = true }
            }
        }
    )

    EnterAmountScreenContent(
        uiState = uiState,
        onBackClick = navigator::navigateUp,
        onAmountChanged = viewModel::onAmountChanged,
        onRequestMoneyClick = viewModel::onRequestMoneyClick,
        onSendMoneyClick = viewModel::onSendMoneyClick,
        flowType = args.flowType
    )
}

data class EnterAmountUiState(
    val userName: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val isAmountError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Boolean>