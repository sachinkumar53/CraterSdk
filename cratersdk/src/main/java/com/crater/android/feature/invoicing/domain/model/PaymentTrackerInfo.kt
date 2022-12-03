package com.crater.android.feature.invoicing.domain.model

import com.crater.android.feature.cash.domain.model.Amount

data class PaymentTrackerInfo(
    val id: String,
    val customerName: String,
    val amount: Amount,
)
