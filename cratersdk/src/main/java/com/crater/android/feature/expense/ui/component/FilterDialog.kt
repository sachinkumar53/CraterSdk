package com.crater.android.feature.expense.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.crater.android.R
import com.crater.android.core.ui.*
import com.crater.android.core.ui.icons.Calendar
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.ui.viewmodel.ExpenseViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

@Composable
fun FilterDialog(
    viewModel: ExpenseViewModel,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit
) {
    FancyAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            viewModel.clearFilter()
            onDismissRequest()
        },
        positiveButton = {
            Button(
                onClick = {
                    viewModel.showFilteredResult()
                    onDismissRequest()
                },
                content = { Text(text = stringResource(R.string.show_results)) }
            )
        },
        negativeButton = {
            OutlinedButton(
                onClick = onDismissRequest,
                content = { Text(text = stringResource(id = R.string.back)) }
            )
        },
        title = {
            Text(text = stringResource(id = R.string.filter))
        },
        content = {
            FilterDialogContent(vm = viewModel)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDialogContent(vm: ExpenseViewModel) {
    val categories = remember { vm.allCategories }
    val accounts by vm.allAccounts.collectAsState()
    val selectedAccounts = remember { vm.selectedAccounts }
    val selectedCategories = remember { vm.selectedCategories }
    val isAllAccountSelected by remember { vm.isAllAccountSelected }
    val isAllCategorySelected by remember { vm.isAllCategoriesSelected }
    val dialogState = rememberMaterialDialogState()
    var isAccountDropDownVisisble by remember { mutableStateOf(false) }
    var isCategoryDropDownVisisble by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(vertical = AppTheme.dimensions.spacingMedium),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.spacingSmall)
    ) {
        ExposedDropdownMenuBox(
            expanded = isAccountDropDownVisisble,
            onExpandedChange = {
                isAccountDropDownVisisble = !isAccountDropDownVisisble
            }
        ) {

            FancyTextField(
                value = when {
                    isAllAccountSelected -> stringResource(R.string.all_accounts)
                    selectedAccounts.isEmpty() -> stringResource(id = R.string.account)
                    selectedAccounts.size == 1 -> "1 account"
                    else -> "${selectedAccounts.size} accounts"
                },
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(text = stringResource(R.string.all_accounts))
                },
                backgroundColor = Color(0xFF243931)
            )
            MultiSelectAccountDropDown(
                accounts = accounts,
                selectedAccounts = selectedAccounts,
                expanded = isAccountDropDownVisisble,
                onDismissRequest = {
                    isAccountDropDownVisisble = !isAccountDropDownVisisble
                },
                onCheckChange = { account, checked ->
                    vm.onAccountCheckChanged(account, checked)
                }
            )
        }

        ExposedDropdownMenuBox(
            expanded = isCategoryDropDownVisisble,
            onExpandedChange = {
                isCategoryDropDownVisisble = !isCategoryDropDownVisisble
            }
        ) {
            FancyTextField(
                value = when {
                    isAllCategorySelected -> stringResource(R.string.all_categories)
                    selectedCategories.isEmpty() -> stringResource(id = R.string.category)
                    selectedCategories.size == 1 -> selectedCategories.first().name
                    else -> "${selectedCategories.size} categories"
                },
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(text = stringResource(R.string.all_categories))
                },
                backgroundColor = Color(0xFF243931)
            )

            MultiSelectCategoryDropDown(
                onDismissRequest = {
                    isCategoryDropDownVisisble = false
                },
                expanded = isCategoryDropDownVisisble,
                selectedCategories = selectedCategories,
                onCheckChange = { category, checked ->
                    vm.onCategoryChanged(category, checked)
                },
                categories = categories
            )

        }

        Row(modifier = Modifier.fillMaxWidth()) {
            FancyText(
                text = vm.fromDate?.format(DateTimeFormatter.ofPattern("dd-MM-yy")) ?: "From",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        vm.fromDatePicker = true
                        dialogState.show()
                    },
                trailing = {
                    Icon(
                        imageVector = CraterIcons.Calendar,
                        contentDescription = null
                    )
                }
            )
            Spacer(modifier = Modifier.width(AppTheme.dimensions.spacingMedium))
            FancyText(
                text = vm.toDate?.format(DateTimeFormatter.ofPattern("dd-MM-yy")) ?: "To",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        vm.fromDatePicker = false
                        dialogState.show()
                    },
                trailing = {
                    Icon(
                        imageVector = CraterIcons.Calendar,
                        contentDescription = null
                    )
                }
            )
            DatePickerDialog(
                dialogState = dialogState,
                onDateSelect = vm::onDateChanged
            )
        }
    }

}


@Composable
private fun FancyText(
    text: String,
    modifier: Modifier = Modifier,
    trailing: @Composable() (RowScope.() -> Unit)? = null,
    color: Color = LocalTextStyle.current.color
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF243931))
            .then(modifier)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = color
        )
        if (trailing != null) trailing()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExposedDropdownMenuBoxScope.MultiSelectCategoryDropDown(
    modifier: Modifier = Modifier,
    categories: Array<ExpenseCategory>,
    selectedCategories: List<ExpenseCategory>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onCheckChange: (ExpenseCategory, Boolean) -> Unit
) {
    if (categories.isNotEmpty()) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .exposedDropdownSize()
                .heightIn(max = 240.dp)
                .background(Color(0xFF243931))
        ) {
            categories.forEach { category ->
                CheckableCategoryDropDownItem(
                    category = category,
                    isChecked = selectedCategories.contains(category),
                    onCheckChange = {
                        onCheckChange(category, it)
                    }
                )
            }
        }
    }
}


@Composable
private fun CheckableCategoryDropDownItem(
    category: ExpenseCategory,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit
) {
    DropdownMenuItem(
        onClick = {
            onCheckChange(!isChecked)
        }
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(28.dp)
                .background(colorResource(id = R.color.primary)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.iconImageVector,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = category.name,
            modifier = Modifier.weight(1f)
        )
        FancyCheckBox(
            checked = isChecked,
            onCheckedChange = onCheckChange
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExposedDropdownMenuBoxScope.MultiSelectAccountDropDown(
    modifier: Modifier = Modifier,
    accounts: List<BankAccount>,
    selectedAccounts: List<String>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onCheckChange: (BankAccount, Boolean) -> Unit
) {
    if (accounts.isNotEmpty()) {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .exposedDropdownSize()
                .heightIn(max = 240.dp)
                .background(Color(0xFF243931))
        ) {
            accounts.forEach { account ->
                CheckableAccountDropDownItem(
                    account = account,
                    isChecked = selectedAccounts.contains(account.accountNumber),
                    onCheckChange = { checked ->
                        onCheckChange(account, checked)
                    }
                )
            }
        }
    }
}


@Composable
private fun CheckableAccountDropDownItem(
    account: BankAccount,
    isChecked: Boolean,
    onCheckChange: (Boolean) -> Unit
) {
    DropdownMenuItem(
        onClick = {
            onCheckChange(!isChecked)
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        GlideImage(
            imageModel = account.bank,
            modifier = Modifier
                .size(20.dp)
                .aspectRatio(1f),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = account.bank.bankName,
            modifier = Modifier.weight(1f)
        )
        Text(text = account.accountNumber)
        FancyCheckBox(
            checked = isChecked,
            onCheckedChange = onCheckChange
        )
    }
}