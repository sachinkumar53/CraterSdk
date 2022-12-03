package com.crater.android.feature.invoicing.ui.screen.tracker.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.invoicing.ui.screen.invoice.component.AmountDueTextField
import com.crater.android.feature.invoicing.ui.screen.invoice.component.CustomerTextField

@Composable
fun CreatePaymentTrackerScreenContent(
    uiState: CreatePaymentTrackerUiState,
    uiActions: CreatePaymentTrackerUiActions
) {
    val scrollState = rememberScrollState()

    Scaffold(topBar = {
        CenteredTopAppBar(
            title = stringResource(id = R.string.create_new_tracker),
            onBackClick = uiActions.onBackClick
        )
    }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        ) {
            FancyHeader(title = {
                Text(
                    text = stringResource(id = R.string.mandatory),
                    style = MaterialTheme.typography.caption,
                    color = LocalContentColor.current.copy(alpha = 0.66f)
                )
            })

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall),
            ) {
                FancyTextField(
                    value = uiState.trackerNumber,
                    onValueChange = uiActions.onTackerNumberChanged,
                    placeholder = { Text(text = stringResource(id = R.string.tracker_number)) },
                    isError = uiState.hasTrackerNumberError
                )

                FancyTextField(
                    value = uiState.fromUser,
                    onValueChange = uiActions.onFromUserChanged,
                    placeholder = { Text(text = stringResource(id = R.string.from)) },
                    isError = uiState.hasFromUserError
                )

                CustomerTextField(
                    value = uiState.toCustomer?.name ?: "",
                    onAddClick = uiActions.onAddCustomerClick,
                    onEditClick = uiActions.onEditCustomerClick,
                    onDeleteClick = uiActions.onDeleteCustomerClick,
                    isError = uiState.hasToCustomerError
                )

                AmountDueTextField(
                    value = uiState.amount,
                    onValueChange = uiActions.onAmountChanged,
                    isError = uiState.hasAmountError
                )
            }
            FancyButton(
                onClick = uiActions.onSaveButtonClick,
                text = stringResource(id = R.string.save_and_preview),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreatePaymentTrackerScreenContentPreview() {
    CraterTheme {
        CreatePaymentTrackerScreenContent(
            uiState = CreatePaymentTrackerUiState(),
            uiActions = CreatePaymentTrackerUiActions()
        )
    }
}