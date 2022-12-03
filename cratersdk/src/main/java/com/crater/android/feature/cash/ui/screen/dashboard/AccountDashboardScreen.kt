package com.crater.android.feature.cash.ui.screen.dashboard

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.core.common.UiState
import com.crater.android.core.extension.findActivity
import com.crater.android.data.model.profile.UserName
import com.crater.android.feature.cash.domain.model.AccountDetail
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.domain.model.Transaction
import com.crater.android.feature.cash.ui.model.EnterAmountArgs
import com.crater.android.feature.cash.ui.model.FlowType
import com.crater.android.feature.cash.ui.model.TransactionListWrapper
import com.crater.android.feature.destinations.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

@Destination
@Composable
fun AccountDashboardScreen(
    viewModel: AccountDashboardViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<RequestSuccessfulScreenDestination, Boolean>
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val recentTransactions by remember {
        derivedStateOf { uiState.transactions.take(4) }
    }

    resultRecipient.onNavResult {
        if (it is NavResult.Value) viewModel.fetchBankDetails()
    }

    AccountDashboardScreenContent(
        uiState = uiState,
        recentTransactions = recentTransactions,
        onSeeAllClick = {
            navigator.navigate(
                TransactionListScreenDestination(
                    TransactionListWrapper(uiState.transactions)
                )
            )
        },
        onRequestMoneyClick = {
            navigator.navigate(UpiVerificationScreenDestination)
        },
        onSelfTransferClick = {
            navigator.navigate(
                EnterAmountScreenDestination(
                    EnterAmountArgs(
                        accountName = uiState.userName.name,
                        flowType = FlowType.SEND_TO_SELF
                    )
                )
            )
        },
        onCheckDetailsClick = {
            navigator.navigate(
                AccountDetailScreenDestination(
                    userName = uiState.userName,
                    accountDetail = uiState.accountDetail
                )
            )
        },
        onBackClick = navigator::navigateUp
    )
}

data class AccountDashboardUiState(
    val userName: UserName = UserName("", ""),
    val accountDetail: AccountDetail = AccountDetail(
        Amount(0.0),
        "",
        "",
        "",
        ""
    ),
    val transactions: List<Transaction> = emptyList(),
    override val isLoading: Boolean = false,
    override val successEvent: StateEventWithContent<Unit> = consumed(),
    override val errorEvent: StateEventWithContent<String> = consumed()
) : UiState<Unit>
