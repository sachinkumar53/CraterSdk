package com.crater.android.feature.cash.ui.screen.common

import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.crater.android.R
import com.crater.android.core.ui.CraterIcons
import com.crater.android.core.ui.FancyListItem
import com.crater.android.core.ui.icons.Wallet
import com.crater.android.core.ui.theme.CraterTheme
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.domain.model.Transaction
import com.crater.android.feature.expense.domain.model.TransactionType.Credit

@Composable
internal fun TransactionListItem(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    FancyListItem(
        icon = {
            Icon(
                imageVector = CraterIcons.Wallet,
                contentDescription = null
            )
        },
        text = {
            Text(
                text = buildString {
                    if (transaction.transactionType == Credit) {
                        append(stringResource(id = R.string.money_received_from))
                    } else {
                        append(stringResource(id = R.string.money_sent_to))
                    }
                    append("\n")
                    append(transaction.title)
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryText = { Text(transaction.date) },
        trailingText = {
            Text(
                text = buildString {
                    append((if (transaction.transactionType == Credit) '+' else '-'))
                    append(transaction.depositAmount.displayValue)
                },
                color = if (transaction.transactionType == Credit) {
                    MaterialTheme.colors.primary
                } else {
                    LocalContentColor.current
                }
            )
        },
        secondaryTrailingText = { Text("Bal: ${transaction.availableAmount.displayValue}") },
        modifier = modifier
    )
}

@Preview
@Composable
fun TransactionListItemPreview() {
    CraterTheme {
        TransactionListItem(
            transaction = Transaction(
                "Money received from Rahul",
                date = "19 May 2022",
                depositAmount = Amount(1200.0),
                transactionType = Credit,
                availableAmount = Amount(5213.2)
            )
        )
    }
}