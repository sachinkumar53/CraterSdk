package com.crater.android.feature.invoicing.ui.screen.addeditcustomer

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
import com.crater.android.core.ui.*
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme

@Composable
fun EditCustomerScreenContent(
    onBackClick: () -> Unit,
    uiState: EditCustomerUiState,
    uiActions: EditCustomerUiActions
) {
    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(if (uiState.isEditMode) R.string.edit_customer else R.string.add_customer),
                onBackClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = AppTheme.dimensions.spacingMedium)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingMedium)
        ) {
            CustomerForm(uiState, uiActions)
            FancyButton(
                text = stringResource(if (uiState.isEditMode) R.string.update else R.string.add),
                onClick = uiActions.onUpdateClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ColumnScope.CustomerForm(
    uiState: EditCustomerUiState,
    uiActions: EditCustomerUiActions
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
    ) {
        FancyHeader(
            title = {
                Text(
                    text = stringResource(id = R.string.mandatory),
                    style = MaterialTheme.typography.caption,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            }
        )

        FancyTextField(
            value = uiState.name,
            onValueChange = uiActions.onNameChanged,
            isError = uiState.isNameError,
            placeholder = { Text(text = stringResource(id = R.string.customer_name)) }
        )

        PhoneTextField(value = uiState.phone, onValueChange = uiActions.onPhoneChanged)
        /*FancyTextField(
            value = uiState.phone,
            onValueChange = {
                if (it.length <= 10) {
                    uiActions.onPhoneChanged(it)
                }
            },
            placeholder = { Text(text = stringResource(id = R.string.phone_number)) },
            //readOnly = true,
            leadingIcon = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "+91")
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(24.dp)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )*/

        FancyHeader(
            title = {
                Text(
                    text = stringResource(id = R.string.optional),
                    style = MaterialTheme.typography.caption,
                    color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                )
            },
            modifier = Modifier.padding(top = AppTheme.dimensions.spacingMedium)
        )

        FancyTextField(
            value = uiState.emailId ?: "",
            onValueChange = uiActions.onEmailChanged,
            placeholder = { Text(text = stringResource(id = R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        FancyTextField(
            value = uiState.gst ?: "",
            onValueChange = uiActions.onGstChanged,
            placeholder = { Text(text = stringResource(id = R.string.gst)) }
        )
        FancyTextField(
            value = uiState.address ?: "",
            onValueChange = uiActions.onAddressChanged,
            placeholder = { Text(text = stringResource(id = R.string.address)) }
        )
        FancyTextField(
            value = uiState.city ?: "",
            onValueChange = uiActions.onCityChanged,
            placeholder = { Text(text = stringResource(id = R.string.city)) }
        )
        FancyTextField(
            value = uiState.state ?: "",
            onValueChange = uiActions.onStateChanged,
            placeholder = { Text(text = stringResource(id = R.string.state)) }
        )
        FancyTextField(
            value = uiState.pinCode ?: "",
            onValueChange = uiActions.onPinCodeChanged,
            placeholder = { Text(text = stringResource(id = R.string.pin_code)) }
        )
        FancyTextField(
            value = uiState.country ?: "",
            onValueChange = uiActions.onCountryChanged,
            placeholder = { Text(text = stringResource(id = R.string.country)) }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun EditCustomerScreenContentPreview() {
    CraterTheme {
        EditCustomerScreenContent(
            onBackClick = {},
            uiState = EditCustomerUiState(),
            uiActions = EditCustomerUiActions()
        )
    }
}