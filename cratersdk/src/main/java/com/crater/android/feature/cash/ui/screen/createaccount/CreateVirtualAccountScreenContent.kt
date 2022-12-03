package com.crater.android.feature.cash.ui.screen.createaccount

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.icons.Cancel
import com.crater.android.core.ui.screen.LoadingScreen
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.ui.model.UpiValidationStatus

@Composable
fun CreateVirtualAccountScreenContent(
    onBackClick: () -> Unit,
    onCreateClick: () -> Unit,
    onUpiIdChanged: (String) -> Unit,
    uiState: CreateVirtualAccountUiState
) {
    Scaffold(
        topBar = { CenteredTopAppBar(title = "", onBackClick = onBackClick) },
        bottomBar = {
            FancyButton(
                onClick = onCreateClick,
                text = stringResource(R.string.create),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimensions.spacingMedium),
                enabled = uiState.upiValidationStatus == UpiValidationStatus.Available

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            Text(
                text = stringResource(R.string.create_your_custom_upi_id),
                style = AppTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingMedium))
            FancyTextField(
                value = uiState.upiId,
                onValueChange = onUpiIdChanged,
                placeholder = { Text(text = stringResource(R.string.custom_upi_id)) },
                isError = uiState.hasUpiIdError,
                trailingIcon = {
                    Text(
                        text = "@yesbank",
                        modifier = Modifier.padding(end = AppTheme.dimensions.spacingSmall),
                        style = AppTheme.typography.labelLarge,
                        color = AppTheme.colors.textPrimary
                    )
                }
            )
            Spacer(modifier = Modifier.height(AppTheme.dimensions.spacingTiny))
            UpiAvailabilityStatus(
                uiState.upiValidationStatus,
                modifier = Modifier.padding(start = AppTheme.dimensions.spacingTiny)
            )
        }
    }
    if (uiState.isLoading) LoadingScreen()
}

@Composable
fun UpiAvailabilityStatus(
    upiValidationStatus: UpiValidationStatus,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingExtraSmall)
    ) {
        when (upiValidationStatus) {
            UpiValidationStatus.Verifying -> CircularProgressIndicator(
                modifier = Modifier.size(AppTheme.dimensions.iconSmall),
                strokeWidth = 2.dp
            )
            UpiValidationStatus.Available -> Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.dimensions.iconSmall),
                tint = AppTheme.colors.primary
            )

            UpiValidationStatus.Unavailable -> Icon(
                imageVector = Icons.Filled.Cancel,
                contentDescription = null,
                modifier = Modifier.size(AppTheme.dimensions.iconSmall),
                tint = AppTheme.colors.error
            )
            UpiValidationStatus.None -> Unit
        }

        when (upiValidationStatus) {
            UpiValidationStatus.Verifying -> Text(
                text = stringResource(R.string.checking_availablity),
                style = AppTheme.typography.labelMedium
            )
            UpiValidationStatus.Available -> Text(
                text = stringResource(R.string.available),
                style = AppTheme.typography.labelMedium,
                color = AppTheme.colors.primary
            )
            UpiValidationStatus.Unavailable -> Text(
                text = stringResource(R.string.not_available),
                color = AppTheme.colors.error,
                style = AppTheme.typography.labelMedium
            )
            UpiValidationStatus.None -> Unit
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateAccountScreenContentPreview() {
    CraterTheme {
        CreateVirtualAccountScreenContent(
            onCreateClick = {},
            onUpiIdChanged = {},
            onBackClick = {},
            uiState = CreateVirtualAccountUiState()
        )
    }
}