package com.crater.android.feature.cash.ui.screen.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.core.util.DateFormatterUtil
import com.crater.android.data.model.profile.UserName
import com.crater.android.feature.cash.domain.model.AccountDetail
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.domain.model.Transaction
import com.crater.android.feature.cash.ui.screen.common.TransactionListItem
import com.crater.android.feature.cash.ui.screen.dashboard.components.BalanceDetails
import com.crater.android.feature.cash.ui.screen.dashboard.components.NoTransactions
import com.crater.android.feature.expense.domain.model.TransactionType
import java.time.LocalDate

@Composable
fun AccountDashboardScreenContent(
    onBackClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onRequestMoneyClick: () -> Unit,
    onSelfTransferClick: () -> Unit,
    onCheckDetailsClick: () -> Unit,
    uiState: AccountDashboardUiState,
    recentTransactions: List<Transaction>
) {
    Scaffold(topBar = {
        CenteredTopAppBar(
            title = stringResource(id = R.string.crater_cash),
            onBackClick = onBackClick
        )
    }) {
        Column {
            BalanceDetails(
                availableBalance = uiState.accountDetail.availableBalance,
                onRequestMoneyClick = onRequestMoneyClick,
                onSelfTransferClick = onSelfTransferClick,
                onCheckDetailsClick = onCheckDetailsClick
            )

            RecentTransactionsHeader(onSeeAllClick = onSeeAllClick)

            if (recentTransactions.isEmpty()) {
                NoTransactions(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
                ) {
                    items(recentTransactions) { item ->
                        TransactionListItem(transaction = item)
                    }
                }
            }

        }
    }

    if (uiState.isLoading)
        LoadingScreen()
}


@Composable
private fun RecentTransactionsHeader(onSeeAllClick: () -> Unit) {
    FancyHeader(
        title = {
            Text(text = stringResource(id = R.string.recent_transactions))
        }, trailing = {
            Text(
                text = stringResource(id = R.string.see_all),
                modifier = Modifier
                    .clickable(onClick = onSeeAllClick)
                    .padding(AppTheme.dimensions.spacingTiny)
            )
        },
        modifier = Modifier
            .padding(horizontal = AppTheme.dimensions.spacingMedium)
            .padding(top = AppTheme.dimensions.spacingLarge)
    )
}


@Preview(showSystemUi = true)
@Composable
fun AccountDetailPreview() {
    val transactions = remember {
        (1..10).map {
            val debit = it % 3 == 0
            Transaction(
                title = if (debit) "Money sent to User$it" else "Money received from User$it",
                date = DateFormatterUtil.formatInMediumStyle(
                    LocalDate.now().minusDays(it.toLong())
                ),
                depositAmount = Amount((10..1000).random().toDouble()),
                availableAmount = Amount((10..1000).random().toDouble()),
                transactionType = if (debit) TransactionType.Debit else TransactionType.Credit
            )
        }
    }
    CraterTheme {
        AccountDashboardScreenContent(
            uiState = AccountDashboardUiState(
                userName = UserName("Rahul", ""),
                accountDetail = AccountDetail(
                    availableBalance = Amount(15236.255),
                    upiId = "462520202210999243@yesbank",
                    accountNo = "",
                    ifsc = "",
                    upiQr = ""
                ),
                transactions = transactions,
            ),
            onSeeAllClick = {},
            recentTransactions = transactions.take(4),
            onSelfTransferClick = {},
            onRequestMoneyClick = {},
            onCheckDetailsClick = {},
            onBackClick = {}
        )
    }
}
