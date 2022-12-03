package com.crater.android.feature.invoicing.ui.screen.invoice.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.CenteredTopAppBar
import com.crater.android.core.ui.FancyButton
import com.crater.android.core.ui.FancyHeader
import com.crater.android.core.ui.FancyTextField
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.invoicing.domain.model.Service
import com.crater.android.feature.invoicing.ui.screen.createinvoice.components.DatePickerTextField
import com.crater.android.feature.invoicing.ui.screen.createinvoice.components.Table
import com.crater.android.feature.invoicing.ui.screen.invoice.component.AmountDueTextField
import com.crater.android.feature.invoicing.ui.screen.invoice.component.CustomerTextField
import com.crater.android.feature.invoicing.ui.screen.invoice.create.components.TaxTextField

@Composable
fun CreateInvoiceScreenContent(
    uiState: CreateInvoiceUiState,
    uiActions: CreateInvoiceUiActions,
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CenteredTopAppBar(
                title = stringResource(id = R.string.create_invoice),
                onBackClick = uiActions.onBackClick
            )
        }
    ) {
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
                    value = uiState.invoiceNumber,
                    onValueChange = uiActions.onInvoiceNumberChanged,
                    placeholder = { Text(text = stringResource(id = R.string.invoice_number)) },
                    isError = uiState.hasInvoiceNumberError
                )

                FancyTextField(
                    value = uiState.fromUser,
                    onValueChange = uiActions.onFromuserChanged,
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

                ServicesSection(
                    services = uiState.services,
                    isError = uiState.hasServicesError,
                    onAddServiceClick = uiActions.onAddServiceClick
                )

                TaxTextField(
                    value = uiState.tax,
                    onValueChange = uiActions.onTaxChanged,
                    taxType = uiState.taxType,
                    onTaxTypeChanged = uiActions.onTaxTypeChanged
                )

                AmountDueTextField(
                    value = uiState.amount,
                    onValueChange = uiActions.onAmountChanged,
                    isError = uiState.hasAmountError,
                    isReadOnly = true
                )

                DatePickerTextField(
                    date = uiState.creationDate,
                    onDateChanged = uiActions.onCreationDateChanged,
                    label = stringResource(id = R.string.created_on)
                )

                DatePickerTextField(
                    date = uiState.dueDate,
                    onDateChanged = uiActions.onDueDateChanged,
                    label = stringResource(id = R.string.due_date),
                    isError = uiState.hasDueDateError
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

@Composable
private fun ServicesSection(
    services: List<Service>,
    onAddServiceClick: () -> Unit,
    isError: Boolean
) {
    Column(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(AppTheme.colors.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .clickable { onAddServiceClick() }
                .padding(AppTheme.dimensions.spacingMedium)
        ) {
            Text(
                text = stringResource(id = R.string.add_service),
                color = if (isError) MaterialTheme.colors.error else LocalContentColor.current
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
        if (services.isNotEmpty()) {
            Table(
                services = services,
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimensions.spacingMedium)
                    .padding(bottom = AppTheme.dimensions.spacingMedium)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CreateInvoiceScreenContentPreview() {
    CraterTheme {
        CreateInvoiceScreenContent(
            uiState = CreateInvoiceUiState(),
            uiActions = CreateInvoiceUiActions()
        )
    }
}
