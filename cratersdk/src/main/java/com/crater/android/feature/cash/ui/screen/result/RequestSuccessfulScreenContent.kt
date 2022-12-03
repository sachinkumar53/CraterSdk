package com.crater.android.feature.cash.ui.screen.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.components.ResultAnimation
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.ui.model.FlowType
import com.crater.android.feature.cash.ui.model.ResultSuccessArgs

@Composable
fun RequestSuccessfulScreenContent(
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onShareClick: () -> Unit,
    args: ResultSuccessArgs
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(onBackClick = onBackClick, title = "")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ResultAnimation(isSuccess = true)

                Text(
                    text = when (args.flowType) {
                        FlowType.REQUEST_MONEY -> stringResource(
                            R.string.request_money_successful,
                            args.amount.displayValue
                        )
                        FlowType.SEND_TO_SELF -> stringResource(
                            R.string.send_to_self_successful,
                            args.amount.displayValue
                        )
                    },
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )

                when (args.flowType) {
                    FlowType.REQUEST_MONEY -> Unit
                    FlowType.SEND_TO_SELF -> {
                        Text(
                            text = stringResource(id = R.string.check_bank_account),
                            style = AppTheme.typography.bodyMedium,
                            color = AppTheme.colors.textSecondary,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .padding(top = AppTheme.dimensions.spacingMedium),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            FancyButton(
                onClick = onDoneClick,
                text = stringResource(id = R.string.done),
                modifier = Modifier.fillMaxWidth()
            )

            if (args.flowType == FlowType.REQUEST_MONEY) {
                OutlinedButton(
                    onClick = onShareClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(AppTheme.dimensions.minButtonHeight)
                        .padding(top = AppTheme.dimensions.spacingMedium)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = null,
                        modifier = Modifier.padding(end = AppTheme.dimensions.spacingSmall)
                    )
                    Text(text = stringResource(R.string.share_payment_link))
                }
            }
        }
    }
    if (isLoading) LoadingScreen()
}

/*
@Composable
private fun TransactionDetail(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            color = MaterialTheme.typography.body2.color
        )

        Text(
            text = value,
            fontWeight = FontWeight.SemiBold
        )
    }
}*/

@Preview(showSystemUi = true)
@Composable
fun RequestSuccessfulScreenContentPreview() {
    CraterTheme {
        RequestSuccessfulScreenContent(
            onBackClick = {},
            args = ResultSuccessArgs(
                amount = Amount(100.0),
                //payerUpiId = "4868855269523@upi",
                flowType = FlowType.SEND_TO_SELF
            ),
            onDoneClick = {},
            onShareClick = {},
            isLoading = false
        )
    }
}