package com.crater.android.feature.cash.ui.screen.enter_amount

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.cash.ui.screen.enter_amount.components.EnterAmountSection

@Composable
fun EnterAmountForSendToSelfContent(
    onBackClick: () -> Unit,
    uiState: EnterAmountUiState,
    onAmountChanged: (String) -> Unit,
    onSendMoneyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.send_to_self)
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = AppTheme.dimensions.spacingMedium)
        ) {
            EnterAmountSection(
                label = stringResource(R.string.transfer_to),
                title = uiState.userName,
                text = uiState.accountNumber,
                amount = uiState.amount,
                onAmountChanged = onAmountChanged
            )

            Spacer(modifier = Modifier.weight(1f))

            FancyButton(
                onClick = onSendMoneyClick,
                text = stringResource(id = R.string.send_money),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
            )
        }
    }
}