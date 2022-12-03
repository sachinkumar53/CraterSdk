package com.crater.android.feature.cash.ui.screen.transaction_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.screen.EmptyScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.cash.ui.model.TransactionListWrapper
import com.crater.android.feature.cash.ui.screen.common.TransactionListItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun TransactionListScreen(
    wrapper: TransactionListWrapper,
    navigator: DestinationsNavigator
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.transactions),
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    ) { padding ->
        if (wrapper.transactions.isEmpty()) {
            EmptyScreen()
        } else {
            LazyColumn(
                modifier = Modifier.padding(AppTheme.dimensions.spacingMedium),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
                contentPadding = padding
            ) {
                items(wrapper.transactions) { item ->
                    TransactionListItem(transaction = item)
                }
            }
        }
    }
}