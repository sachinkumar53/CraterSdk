package com.crater.android.feature.authentication.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.extension.showToast
import com.crater.android.feature.authentication.domain.model.AuthFlowType
import com.crater.android.feature.destinations.LoginScreenDestination
import com.crater.android.feature.destinations.OtpScreenDestination
import com.crater.android.feature.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect

@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            navigator.navigate(
                OtpScreenDestination(
                    flowType = AuthFlowType.LOGIN,
                    phoneNumber = uiState.phoneNumber,
                    inviteCode = null
                )
            )
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    LoginScreenContent(
        onContinueClick = viewModel::onContinueClick,
        onRegisterClick = {
            navigator.navigate(RegisterScreenDestination()) {
                popUpTo(LoginScreenDestination) { inclusive = true }
            }
        },
        onPhoneNumberChanged = viewModel::onPhoneChanged,
        uiState = uiState
    )

}