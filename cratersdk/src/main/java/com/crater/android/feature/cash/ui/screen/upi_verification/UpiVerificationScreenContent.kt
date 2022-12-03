package com.crater.android.feature.cash.ui.screen.upi_verification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun UpiVerificationScreenContent(
    onBackClick: () -> Unit,
    onUpiIdChanged: (String) -> Unit,
    onContinueClick: () -> Unit,
    uiState: UpiVerificationUiState
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(onBackClick = onBackClick, title = "")
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(AppTheme.dimensions.spacingMedium)
            ) {
                Text(
                    text = stringResource(R.string.enter_payers_upi_id),
                    style = AppTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = AppTheme.dimensions.spacingTiny)
                )

                Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))

                FancyTextField(
                    value = uiState.upiId,
                    onValueChange = onUpiIdChanged,
                    placeholder = {
                        Text(text = stringResource(R.string.upi_id))
                    },
                    isError = uiState.isUpiIdError,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        onContinueClick()
                    })
                )

                Spacer(modifier = Modifier.weight(1f))

                FancyButton(
                    onClick = onContinueClick,
                    text = stringResource(R.string.verify_and_continue),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (uiState.isLoading) LoadingScreen()
        }
    )

}


@Preview(showSystemUi = true)
@Composable
fun UpiVerificationScreenContentPreview() {
    CraterTheme {
        UpiVerificationScreenContent(
            onBackClick = {},
            uiState = UpiVerificationUiState(),
            onContinueClick = {},
            onUpiIdChanged = {}
        )
    }
}