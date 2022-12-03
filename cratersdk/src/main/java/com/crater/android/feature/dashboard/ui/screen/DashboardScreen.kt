package com.crater.android.feature.dashboard.ui.screen

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.openActivity
import com.crater.android.core.extension.showToast
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.feature.destinations.*
import com.crater.android.feature.social.ui.SocialMediaActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@RootNavGraph(start = true)
@Destination
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    EventEffect(
        event = uiState.errorEvent,
        onConsumed = viewModel::onErrorEventConsumed,
        action = context::showToast
    )

    DashboardScreenContent(
        userName = uiState.userDetails.userName.firstName,
        onProfileClick = {
            navigator.navigate(ProfileScreenDestination())
        },
        onCreateInvoiceClick = {
            navigator.navigate(InvoiceDashboardScreenDestination())
        },
        onCraterCashClick = {
            val isAccountCreated = uiState.userDetails.isVirtualAccountCreated
            if (isAccountCreated) {
                navigator.navigate(AccountDashboardScreenDestination())
            } else {
                navigator.navigate(AccountFeaturesScreenDestination())
            }
        },
        onExpenseManagerClick = {
            navigator.navigate(ExpenseSummaryScreenDestination())
        },
        onFileTaxClick = {
            navigator.navigate(FileTaxScreenDestination())
        },
        onSocialMediaClick = {
            (context as Activity).openActivity(SocialMediaActivity::class.java)
        }
    )
}


data class DashboardUiState(
    val userDetails: UserDetails = UserDetails(),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed(),
) : UiState<Unit>