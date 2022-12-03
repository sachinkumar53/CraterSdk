package com.crater.android.feature.cash.ui.model

import com.crater.android.feature.cash.domain.model.Transaction
import kotlinx.serialization.Serializable

@Serializable
data class TransactionListWrapper(
    val transactions: List<Transaction>
)
