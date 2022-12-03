package com.crater.android.feature.cash.domain.model

import com.crater.android.feature.expense.domain.model.TransactionType
import kotlinx.serialization.Serializable

@Serializable
data class Transaction(
    val title:String,
    val date: String,
    val depositAmount: Amount,
    val availableAmount: Amount,
    val transactionType: TransactionType,
)
