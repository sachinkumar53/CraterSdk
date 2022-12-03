package com.crater.android.feature.invoicing.domain.model

import com.crater.android.feature.cash.domain.model.Amount

data class PaymentTrackerPreviewDetail(
    val tackerNumber: String,
    val dueDate: String,
    val fromUserInfo: UserInfo,
    val customerInfo: CustomerInfo,
    val amountDue: Amount,
    val amountPaid: Amount
)