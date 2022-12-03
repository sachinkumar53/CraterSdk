package com.crater.android.feature.authentication.ui.screen.otp

import android.content.IntentFilter
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.findActivity
import com.crater.android.core.extension.showToast
import com.crater.android.feature.authentication.domain.model.AuthFlowType
import com.crater.android.feature.authentication.ui.model.OtpScreenArgs
import com.crater.android.feature.destinations.DashboardScreenDestination
import com.crater.android.feature.destinations.LoginScreenDestination
import com.crater.android.feature.destinations.PersonalDetailsScreenDestination
import com.crater.android.feature.destinations.RegisterScreenDestination
import com.crater.android.receiver.OTPBroadcastReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination(navArgsDelegate = OtpScreenArgs::class)
@Composable
fun OtpScreen(
    viewModel: OtpViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val otpBroadcastReceiver = remember { OTPBroadcastReceiver() }

    DisposableEffect(otpBroadcastReceiver, context) {
        val activity = context.findActivity()
        activity?.let { SmsRetriever.getClient(it) }?.startSmsRetriever()

        /*task.addOnSuccessListener {
            // Successfully started retriever, expect broadcast intent
            //Log.i(TAG, "startSMSRetrieverClient: Started successfully")
        }

        task.addOnFailureListener {
            // Failed to start retriever, inspect Exception for more details
            //Log.e(TAG, "startSMSRetrieverClient: Failed to start sms retriever", it)
        }*/
        context.registerReceiver(
            otpBroadcastReceiver,
            IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        )
        otpBroadcastReceiver.setOTPReceiveListener {
            if (it != null) {
                viewModel.onOtpChanged(it)
                viewModel.onVerifyClick()
            }
        }
        onDispose {
            otpBroadcastReceiver.setOTPReceiveListener(null)
            context.unregisterReceiver(otpBroadcastReceiver)
        }
    }

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed,
        action = {
            when (viewModel.args.flowType) {
                AuthFlowType.LOGIN -> {
                    navigator.navigate(DashboardScreenDestination()) {
                        popUpTo(LoginScreenDestination) { inclusive = true }
                    }
                }
                AuthFlowType.REGISTRATION -> navigator.navigate(PersonalDetailsScreenDestination()) {
                    popUpTo(RegisterScreenDestination) { inclusive = true }
                }
            }
        }
    )

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    OtpScreenContent(
        onBackClick = navigator::navigateUp,
        onVerifyClick = viewModel::onVerifyClick,
        phoneNumber = viewModel.args.phoneNumber,
        onOtpChanged = viewModel::onOtpChanged,
        onResendClick = viewModel::onResendClick,
        uiState = uiState
    )
}

data class OtpUiState(
    val otp: String = "",
    val hasOtpError: Boolean = false,
    val remainingTimeToResendOtp: Int = 0,
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Boolean> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Boolean>