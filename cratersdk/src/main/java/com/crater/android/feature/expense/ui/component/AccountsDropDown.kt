package com.crater.android.feature.expense.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.theme.AppTheme
import com.crater.android.feature.expense.domain.BankAccount
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxScope.AccountsDropDown(
    modifier: Modifier = Modifier,
    accounts: List<BankAccount>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemClick: (BankAccount) -> Unit
) {
    if (accounts.isNotEmpty()) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier
                .exposedDropdownSize()
                .heightIn(max = 240.dp)
                .background(AppTheme.colors.surfaceVariant)
        ) {
            accounts.forEach {
                AccountDropDownItem(
                    account = it,
                    onItemClick = {
                        onDismissRequest()
                        onItemClick(it)
                    }
                )
            }
        }
    }
}

@Composable
fun AccountDropDownItem(
    account: BankAccount,
    onItemClick: () -> Unit
) {
    DropdownMenuItem(
        onClick = onItemClick,
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
    }
}