package com.crater.android.feature.kyc.ui.screen.pan_verification

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.DashboardScreenDestination
import com.crater.android.feature.destinations.PanVerificationResultScreenDestination
import com.crater.android.feature.destinations.PersonalDetailsScreenDestination
import com.crater.android.feature.kyc.ui.model.KycFlowType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect

@Destination
@Composable
fun PanVerificationScreen(
    viewModel: PanVerificationViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    kycFlowType: KycFlowType
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            navigator.navigate(PanVerificationResultScreenDestination(it, kycFlowType))
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    PanVerificationScreenContent(
        onBackClick = navigator::navigateUp,
        onContinueClick = { viewModel.verifyPanNumber { focusManager.clearFocus() } },
        onSkipClick = {
            navigator.navigate(DashboardScreenDestination) {
                popUpTo(PersonalDetailsScreenDestination) { inclusive = true }
            }
        },
        onPanValueChange = viewModel::onPanNumberChanged,
        uiState = uiState,
        kycFlowType = kycFlowType
    )
}
