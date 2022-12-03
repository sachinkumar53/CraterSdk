package com.crater.android.feature.invoicing.ui.screen.tracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.invoicing.domain.model.PaymentTrackerInfo
import com.crater.android.feature.invoicing.ui.common.EmptyListPlaceholder
import com.crater.android.feature.invoicing.ui.common.PaymentTrackerCard
import com.crater.android.feature.invoicing.ui.screen.tracker.component.PaymentTrackerListItem

@Composable
fun PaymentTrackerScreenContent(
    onCreateTrackerButtonClick: () -> Unit,
    onSeeAllClick: () -> Unit,
    onTrackerClick: (PaymentTrackerInfo) -> Unit,
    uiState: PaymentTrackerUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
    ) {
        PaymentTrackerCard(tracker = uiState.tracker)
        FancyButton(
            onClick = onCreateTrackerButtonClick,
            text = stringResource(id = R.string.create_new_tracker),
            modifier = Modifier.fillMaxWidth()
        )
        FancyHeader(
            title = { Text(text = stringResource(id = R.string.recent_trackings)) },
            trailing = {
                Text(
                    text = stringResource(id = R.string.see_all),
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }
        )

        val infos = uiState.recentTrackerList
        if (infos.isEmpty()) {
            EmptyListPlaceholder(
                text = stringResource(id = R.string.no_payments_yet),
                description = stringResource(id = R.string.start_tracking_payments_now),
                modifier = Modifier.weight(1f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
            ) {
                items(infos) {
                    PaymentTrackerListItem(
                        info = it,
                        onItemClick = { onTrackerClick(it) }
                    )
                }
            }
        }
    }
    if (uiState.isLoading) LoadingScreen()
}

@Preview(showSystemUi = true)
@Composable
fun PaymentTrackerScreenContentPreview() {
    CraterTheme {
        PaymentTrackerScreenContent(
            uiState = PaymentTrackerUiState(),
            onCreateTrackerButtonClick = {},
            onSeeAllClick = {},
            onTrackerClick = {}
        )
    }
}