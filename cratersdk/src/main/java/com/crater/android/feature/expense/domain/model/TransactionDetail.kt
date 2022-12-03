package com.crater.android.feature.expense.domain.model

import com.crater.android.feature.expense.data.sms.merchant.Bank

data class TransactionDetail(
    val merchantName: String,
    val amount: String,
    val date: String,
    val category: ExpenseCategory,
    val transactionType: TransactionType,
    val refNumber: String?,
    val accountNumber: String?,
    val cardNumber: String?,
    val bank: Bank
)