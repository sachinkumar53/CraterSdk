package com.crater.android.feature.cash.ui.screen.result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.core.extension.startTextShareIntent
import com.crater.android.feature.cash.ui.model.FlowType
import com.crater.android.feature.cash.ui.model.ResultSuccessArgs
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination(navArgsDelegate = ResultSuccessArgs::class)
@Composable
fun RequestSuccessfulScreen(
    viewModel: ResultViewModel = hiltViewModel(),
    args: ResultSuccessArgs,
    resultNavigator: ResultBackNavigator<Boolean>
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = { paymentLink ->
            context.startTextShareIntent(
                text = buildString {
                    appendLine("Hey there!")
                    appendLine("I have sent you a request for ${args.amount.displayValue}.")
                    appendLine("Use the link below to pay the same.")
                    appendLine()//For one line gap
                    appendLine(paymentLink)
                }
            )
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    RequestSuccessfulScreenContent(
        isLoading = uiState.isLoading,
        args = args,
        onBackClick = resultNavigator::navigateBack,
        onDoneClick = {
            if (args.flowType == FlowType.SEND_TO_SELF) {
                resultNavigator.navigateBack(true)
            } else resultNavigator.navigateBack()
        },
        onShareClick = viewModel::onShareClick
    )
}

data class ResultUiState(
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<String> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<String>