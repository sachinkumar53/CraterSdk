package com.crater.android.feature.cash.ui.screen.enter_amount

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.ui.model.FlowType

@Composable
fun EnterAmountScreenContent(
    onBackClick: () -> Unit,
    onAmountChanged: (String) -> Unit,
    onRequestMoneyClick: () -> Unit,
    onSendMoneyClick: () -> Unit,
    uiState: EnterAmountUiState,
    flowType: FlowType
) {
    when (flowType) {
        FlowType.REQUEST_MONEY -> {
            EnterAmountForRequestMoneyContent(
                uiState = uiState,
                onAmountChanged = onAmountChanged,
                onRequestMoneyClick = onRequestMoneyClick,
                onBackClick = onBackClick
            )
        }
        FlowType.SEND_TO_SELF -> {
            EnterAmountForSendToSelfContent(
                uiState = uiState,
                onAmountChanged = onAmountChanged,
                onSendMoneyClick = onSendMoneyClick,
                onBackClick = onBackClick
            )
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun EnterAmountScreenContentPreview() {
    CraterTheme {
        EnterAmountScreenContent(
            onBackClick = {},
            onRequestMoneyClick = {},
            onAmountChanged = {},
            uiState = EnterAmountUiState(
                userName = "Sachin Kumar",
                accountNumber = "74995259752651@upi"
            ),
            onSendMoneyClick = {},
            flowType = FlowType.SEND_TO_SELF
        )
    }
}
