package com.crater.android.feature.kyc.ui.screen.bank_verification

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.components.ResultAnimation
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.destinations.AccountFeaturesScreenDestination
import com.crater.android.feature.destinations.CreateVirtualAccountScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@Composable
fun BankVerificationResult(navigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(onBackClick = navigator::navigateUp, title = "")
        },
        bottomBar = {
            FancyButton(
                onClick = {
                    navigator.navigate(CreateVirtualAccountScreenDestination()) {
                        popUpTo(AccountFeaturesScreenDestination) {
                            inclusive = false
                        }
                    }
                },
                text = stringResource(id = R.string.proceed),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(AppTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            ResultAnimation(isSuccess = true)
            Spacer(modifier = Modifier.padding(AppTheme.dimensions.spacingMedium))
            Text(
                text = stringResource(id = R.string.successfully_deposited_1_to_your_account),
                style = AppTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun BankVerificationResultPreview() {
    CraterTheme {
        BankVerificationResult(
            navigator = EmptyDestinationsNavigator
        )
    }
}