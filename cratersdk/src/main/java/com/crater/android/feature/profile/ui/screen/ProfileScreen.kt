package com.crater.android.feature.profile.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.BuildConfig
import com.crater.android.R
import com.crater.android.core.extension.showToast
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.destinations.DashboardScreenDestination
import com.crater.android.feature.destinations.HelpScreenDestination
import com.crater.android.feature.destinations.LoginScreenDestination
import com.crater.android.feature.destinations.LogoutDialogDestination
import com.crater.android.feature.profile.ui.ProfileViewModel
import com.crater.android.feature.profile.ui.components.NavigationButton
import com.crater.android.feature.profile.ui.components.ProfileHeader
import com.crater.android.feature.profile.ui.navigation.ProfileNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@ProfileNavGraph(start = true)
@Destination
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<LogoutDialogDestination, Boolean>
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    EventEffect(
        event = uiState.successEvent,
        onConsumed = viewModel::onSuccessEventConsumed
    ) {
        context.showToast(it)
        navigator.navigate(LoginScreenDestination()) {
            popUpTo(DashboardScreenDestination) { inclusive = true }
        }
    }
    resultRecipient.onNavResult { result ->
        if (result is NavResult.Value && result.value) {
            viewModel.logout()
        }
    }
    Scaffold(
        topBar = {
            ProfileHeader(
                name = uiState.userName,
                phone = uiState.mobileNumber,
                email = uiState.emailId,
                isVerified = uiState.isAccountVerified
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimensions.spacingMedium)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            NavigationButton(
                title = stringResource(id = R.string.help_and_support),
                description = stringResource(id = R.string.help_and_support_desc),
                onClick = {
                    navigator.navigate(HelpScreenDestination())
                }
            )
            NavigationButton(
                title = stringResource(id = R.string.logout),
                onClick = {
                    navigator.navigate(LogoutDialogDestination())
                }
            )
            /*Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = "Version ${BuildConfig.VERSION_NAME}",
                    style = AppTheme.typography.labelLarge,
                    color = AppTheme.colors.textSecondary
                )
            }*/
        }
    }
}


data class ProfileUiState(
    val userName: String = "",
    val mobileNumber: String = "",
    val emailId: String = "",
    val isAccountVerified: Boolean = false,
    val isLoading: Boolean = false,
    val successEvent: StateEventWithContent<String> = consumed(),
    val errorEvent: StateEventWithContent<String> = consumed()
)
