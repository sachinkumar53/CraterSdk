package com.crater.android.feature.expense.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crater.android.R
import com.crater.android.R.drawable
import com.crater.android.core.ui.*
import com.crater.android.core.ui.icons.ArrowDropDown
import com.crater.android.feature.expense.ui.component.AccountsDropDown
import com.crater.android.feature.expense.ui.component.CategoryDropDownMenu
import com.crater.android.feature.expense.ui.viewmodel.AddExpenseViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun AddExpenseScreen(
    vm: AddExpenseViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val accountDropDownVisible = remember { mutableStateOf(false) }
    val categoryDropDownVisible = remember { mutableStateOf(false) }
    val dialogState = rememberMaterialDialogState()

    val accounts by vm.accountsFlow.collectAsState(initial = emptyList())
    val bankAccount by vm.bankAccount.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CenteredTopAppBar(
            title = stringResource(R.string.add_expense),
            onBackClick = navigator::navigateUp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            FancyTextField(
                value = vm.amount,
                onValueChange = vm::onAmountChanged,
                placeholder = {
                    Text(text = stringResource(R.string.enter_amount))
                },
                isError = vm.isAmountError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                )
            )
            Spacer(modifier = Modifier.height(15.dp))

            FancyReadOnlyTextField(
                value = vm.selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                placeholder = stringResource(R.string.date_of_spend),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = drawable.ic_calendar),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                onClick = dialogState::show
            )
            Spacer(modifier = Modifier.height(15.dp))

            FancyTextField(
                value = vm.paidTo,
                onValueChange = vm::onPaidToChanged,
                placeholder = {
                    Text(text = stringResource(R.string.paid_to))
                },
                isError = vm.isPaidToError
            )
            Spacer(modifier = Modifier.height(15.dp))

            ExposedDropdownMenuBox(
                expanded = accountDropDownVisible.value,
                onExpandedChange = {
                    accountDropDownVisible.value = !accountDropDownVisible.value
                }
            ) {
                FancyTextField(
                    value = bankAccount?.accountNumber ?: "",
                    onValueChange = {},
                    placeholder = {
                        Text(text = stringResource(R.string.from_account))
                    },
                    isError = vm.isAccountError,
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                AccountsDropDown(
                    accounts = accounts,
                    expanded = accountDropDownVisible.value,
                    onDismissRequest = {
                        accountDropDownVisible.value = false
                    },
                    onItemClick = vm::onAccountNumberChanged
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            ExposedDropdownMenuBox(
                expanded = categoryDropDownVisible.value,
                onExpandedChange = {
                    categoryDropDownVisible.value = !categoryDropDownVisible.value
                }
            ) {
                FancyTextField(
                    value = vm.selectedCategory?.name ?: "",
                    placeholder = {
                        Text(text = stringResource(R.string.category))
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = CraterIcons.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    isError = vm.isCategoryError,
                    readOnly = true,
                    onValueChange = vm::onCategoryChanged
                )
                CategoryDropDownMenu(
                    expanded = categoryDropDownVisible.value,
                    onDismissRequest = {
                        categoryDropDownVisible.value = false
                    },
                    onItemClick = vm::onCategoryChanged
                )
            }
            DatePickerDialog(
                dialogState,
                vm::onDateChanged
            )
            val context = LocalContext.current
            Spacer(modifier = Modifier.weight(1f))
            FancyButton(
                text = stringResource(id = R.string.add),
                onClick = {
                    vm.onAddClick {
                        Toast.makeText(context, "Expense added successfully!", Toast.LENGTH_SHORT)
                            .show()
                        navigator.navigateUp()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}


