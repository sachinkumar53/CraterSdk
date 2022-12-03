package com.crater.android.feature.kyc.ui.screen.pan_verification

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.components.ResultAnimation
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.destinations.BankVerificationScreenDestination
import com.crater.android.feature.destinations.DashboardScreenDestination
import com.crater.android.feature.destinations.PanVerificationScreenDestination
import com.crater.android.feature.destinations.PersonalDetailsScreenDestination
import com.crater.android.feature.kyc.ui.model.KycFlowType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@Composable
fun PanVerificationResultScreen(
    isSuccess: Boolean,
    navigator: DestinationsNavigator,
    flowType: KycFlowType
) {
    Scaffold(
        bottomBar = {
            FancyButton(
                onClick = {
                    if (flowType == KycFlowType.BANK_KYC) {
                        navigator.navigate(BankVerificationScreenDestination) {
                            popUpTo(PanVerificationScreenDestination) { inclusive = true }
                        }
                    } else {
                        navigator.navigate(DashboardScreenDestination) {
                            popUpTo(PersonalDetailsScreenDestination) { inclusive = true }
                        }
                    }
                },
                text = stringResource(id = R.string.proceed),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingLarge)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ResultAnimation(isSuccess)

            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))

            Text(
                text = stringResource(
                    id = if (isSuccess) R.string.pan_verification_successful
                    else R.string.pan_verification_failed
                ),
                style = AppTheme.typography.headlineMedium
            )

            Text(
                text = stringResource(
                    id = if (isSuccess) R.string.pan_verified_success_message
                    else R.string.pan_verified_failed_message
                ),
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.textSecondary,
                modifier = Modifier.paddingFromBaseline(AppTheme.dimensions.spacingMedium)
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun VerificationResultPreview() {
    CraterTheme {
        PanVerificationResultScreen(
            isSuccess = true,
            navigator = EmptyDestinationsNavigator,
            flowType = KycFlowType.BANK_KYC
        )
    }
}