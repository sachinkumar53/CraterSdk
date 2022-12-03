package com.crater.android.feature.authentication.ui.screen.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.authentication.domain.model.AuthFlowType
import com.crater.android.feature.destinations.LoginScreenDestination
import com.crater.android.feature.destinations.OtpScreenDestination
import com.crater.android.feature.destinations.RegisterScreenDestination
import com.crater.android.feature.destinations.TermsAndConditionsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            navigator.navigate(
                OtpScreenDestination(
                    flowType = AuthFlowType.REGISTRATION,
                    phoneNumber = uiState.phoneNumber,
                    inviteCode = uiState.inviteCode
                )
            )
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    RegisterScreenContent(
        onTermsAndConditionClick = {
            navigator.navigate(TermsAndConditionsScreenDestination())
        },
        onContinueClick = viewModel::onContinueClick,
        onLoginClick = {
            navigator.navigate(LoginScreenDestination()) {
                popUpTo(RegisterScreenDestination) { inclusive = true }
            }

        },
        onTermsAndConditionCheckChange = viewModel::onTermsAndConditionCheckChange,
        onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
        onInviteCodeChanged = viewModel::onInviteCodeChanged,
        uiState = uiState
    )
}

data class RegisterUiState(
    val phoneNumber: String = "",
    val inviteCode: String = "",
    val isTermsAndConditionChecked: Boolean = false,
    val hasTermsAndConditionError: Boolean = false,
    val hasPhoneNumberError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Boolean>