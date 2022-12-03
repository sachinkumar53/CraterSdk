package com.crater.android.feature.kyc.ui.screen.pan_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.kyc.ui.model.KycFlowType
import com.crater.android.feature.kyc.ui.model.PanVerificationUiState

@Composable
fun PanVerificationScreenContent(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    onSkipClick: () -> Unit,
    onPanValueChange: (String) -> Unit,
    uiState: PanVerificationUiState,
    kycFlowType: KycFlowType
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(onBackClick = onBackClick, title = "")
        },
        bottomBar = {
            Column(
                modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                FancyButton(
                    text = stringResource(id = R.string.txt_continue),
                    onClick = onContinueClick,
                    modifier = Modifier.fillMaxWidth()
                )

                if (kycFlowType == KycFlowType.ON_BOARDING) {
                    SkipVerificationButton(onClick = onSkipClick)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(AppTheme.dimensions.spacingMedium),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingLarge)
        ) {
            Text(
                text = stringResource(id = R.string.enter_your_pan),
                style = AppTheme.typography.headlineMedium
            )

            FancyTextField(
                value = uiState.panNumber?.value ?: "",
                onValueChange = onPanValueChange,
                placeholder = { Text(text = stringResource(id = R.string.pan_card_number)) },
                isError = uiState.isInvalidPanNumber,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    autoCorrect = false,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview(showSystemUi = true)
@Composable
fun PanVerificationScreenContentPreview() {
    CraterTheme {
        PanVerificationScreenContent(
            onBackClick = {},
            onContinueClick = {},
            onSkipClick = {},
            onPanValueChange = {},
            uiState = PanVerificationUiState(),
            kycFlowType = KycFlowType.ON_BOARDING
        )
    }
}