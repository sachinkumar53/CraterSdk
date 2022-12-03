package com.crater.android.feature.authentication.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.PhoneTextField
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.authentication.ui.common.PartiallyClickableText

@Composable
fun LoginScreenContent(
    onContinueClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    uiState: LoginUiState
) {
    Scaffold {
        Column(
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(id = R.string.login),
                style = AppTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingLarge))
            PhoneTextField(
                value = uiState.phoneNumber,
                onValueChange = onPhoneNumberChanged,
                isError = uiState.hasPhoneNumber
            )

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingExtraLarge))

            FancyButton(
                onClick = onContinueClick,
                text = stringResource(id = R.string.txt_continue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimensions.spacingSmall)
            )

            Spacer(modifier = Modifier.weight(1f))

            PartiallyClickableText(
                simpleText = stringResource(id = R.string.dont_have_an_account),
                clickableText = stringResource(id = R.string.create_an_account),
                onClick = onRegisterClick,
                modifier = Modifier
                    .padding(bottom = AppTheme.dimensions.spacingExtraLarge)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
    if (uiState.isLoading) LoadingScreen()
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenContentPreview() {
    CraterTheme {
        LoginScreenContent(
            onContinueClick = {},
            onPhoneNumberChanged = {},
            onRegisterClick = {},
            uiState = LoginUiState()
        )
    }
}