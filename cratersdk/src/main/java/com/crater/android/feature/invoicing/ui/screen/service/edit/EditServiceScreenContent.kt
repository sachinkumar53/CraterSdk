package com.crater.android.feature.invoicing.ui.screen.service.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun EditServiceScreenContent(
    uiState: EditServiceUiState,
    uiActions: EditServiceUiActions
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.add_service),
                onBackClick = uiActions.onBackClick
            )
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium),
            modifier = Modifier.padding(AppTheme.dimensions.spacingMedium)
        ) {
            ServiceForm(uiState, uiActions)

            FancyButton(
                onClick = uiActions.onAddClick,
                text = stringResource(id = R.string.add),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun ColumnScope.ServiceForm(uiState: EditServiceUiState, uiActions: EditServiceUiActions) {
    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
    ) {
        FancyHeader(title = {
            Text(
                text = stringResource(id = R.string.mandatory),
                style = MaterialTheme.typography.caption,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
        })
        FancyTextField(
            value = uiState.serviceName,
            onValueChange = uiActions.onServiceNameChanged,
            placeholder = { Text(text = stringResource(id = R.string.service_name)) }
        )

        FancyTextField(
            value = uiState.price,
            onValueChange = uiActions.onPriceChanged,
            placeholder = { Text(text = stringResource(id = R.string.price)) },
            leadingIcon = { Text(text = "₹") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        FancyTextField(
            value = uiState.quantity,
            onValueChange = uiActions.onQuantityChanged,
            placeholder = { Text(text = stringResource(id = R.string.quantity)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        FancyHeader(title = {
            Text(
                text = stringResource(id = R.string.optional),
                style = MaterialTheme.typography.caption,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
        })
        FancyTextField(
            value = uiState.sacCode,
            onValueChange = uiActions.onSacCodeChanged,
            placeholder = { Text(text = stringResource(id = R.string.sac_code)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun EditServiceScreenContentPreview() {
    CraterTheme {
        EditServiceScreenContent(
            uiState = EditServiceUiState(),
            uiActions = EditServiceUiActions()
        )
    }
}