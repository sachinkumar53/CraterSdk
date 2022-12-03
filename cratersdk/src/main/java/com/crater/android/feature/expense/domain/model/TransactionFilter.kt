package com.crater.android.feature.expense.domain.model

import java.time.LocalDate

class TransactionFilter(
    val isAllAccount: Boolean = true,
    val isAllCategories: Boolean = true,
    val fromDate: LocalDate? = null,
    val toDate: LocalDate? = null,
    val categories: List<ExpenseCategory> = emptyList(),
    val accounts: List<String> = emptyList()
)

fun TransactionFilter.isEmpty(): Boolean {
    return ((isAllAccount && isAllCategories) || (accounts.isEmpty() && categories.isEmpty()))
            && fromDate == null && toDate == null
}
