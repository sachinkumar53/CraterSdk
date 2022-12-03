package com.crater.android.feature.authentication.ui.screen.register.filldetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.showToast
import com.crater.android.feature.destinations.PanVerificationScreenDestination
import com.crater.android.feature.kyc.ui.model.KycFlowType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun PersonalDetailsScreen(
    viewModel: PersonalDetailsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            navigator.navigate(PanVerificationScreenDestination(KycFlowType.ON_BOARDING))
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    PersonalDetailsScreenContent(
        uiState = uiState,
        uiActions = PersonalDetailsUiActions(
            onFirstNameChanged = viewModel::onFirstNameChanged,
            onLastNameChanged = viewModel::onLastNameChanged,
            onGenderChanged = viewModel::onGenderChanged,
            onDobChanged = viewModel::onDobChanged,
            onEmailChanged = viewModel::onEmailChanged,
            onCityChanged = viewModel::onCityChanged,
            onStateChanged = viewModel::onStateChanged,
            onPinCodeChanged = viewModel::onPinCodeChanged,
            onContinueClick = viewModel::onContinueClick,
            onBackClick = navigator::navigateUp
        )
    )
}

data class PersonalDetailsUiState(
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val dob: String = "",
    val email: String = "",
    val city: String = "",
    val state: String = "",
    val pinCode: String = "",
    val hasFirstNameError: Boolean = false,
    val hasLastNameError: Boolean = false,
    val hasGenderError: Boolean = false,
    val hasDobError: Boolean = false,
    val hasEmailError: Boolean = false,
    val hasCityError: Boolean = false,
    val hasStateError: Boolean = false,
    val hasPinCodeError: Boolean = false,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Boolean>

data class PersonalDetailsUiActions(
    val onFirstNameChanged: (String) -> Unit = {},
    val onLastNameChanged: (String) -> Unit = {},
    val onGenderChanged: (String) -> Unit = {},
    val onDobChanged: (String) -> Unit = {},
    val onEmailChanged: (String) -> Unit = {},
    val onCityChanged: (String) -> Unit = {},
    val onStateChanged: (String) -> Unit = {},
    val onPinCodeChanged: (String) -> Unit = {},
    val onBackClick: () -> Unit = {},
    val onContinueClick: () -> Unit = {}
)