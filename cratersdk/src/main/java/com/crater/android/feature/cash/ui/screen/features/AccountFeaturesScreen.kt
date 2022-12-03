package com.crater.android.feature.cash.ui.screen.features

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.destinations.BankVerificationScreenDestination
import com.crater.android.feature.destinations.CreateVirtualAccountScreenDestination
import com.crater.android.feature.destinations.PanVerificationScreenDestination
import com.crater.android.feature.kyc.ui.model.KycFlowType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@Destination
@Composable
fun AccountFeaturesScreen(
    viewModel: FeaturesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    AccountFeatureScreenContent(
        onBackClick = navigator::navigateUp,
        onCompleteKycClick = {
            when {
                !viewModel.isPanKycDone -> {
                    navigator.navigate(PanVerificationScreenDestination(KycFlowType.BANK_KYC))
                }
                !viewModel.isBankKycDone -> {
                    navigator.navigate(BankVerificationScreenDestination())
                }
                !viewModel.isBankAccountCreated -> {
                    navigator.navigate(CreateVirtualAccountScreenDestination())
                }
            }
        }
    )
}

@Preview
@Composable
fun AccountFeaturesPreview() {
    CraterTheme {
        AccountFeaturesScreen(navigator = EmptyDestinationsNavigator)
    }
}