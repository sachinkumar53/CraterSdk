package com.crater.android.feature.expense.domain

import com.crater.android.feature.expense.data.sms.merchant.Bank

data class BankAccount(
    val bank: Bank,
    val accountNumber: String
)
