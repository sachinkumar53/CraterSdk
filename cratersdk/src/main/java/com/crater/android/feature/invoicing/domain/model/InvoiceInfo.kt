package com.crater.android.feature.invoicing.domain.model

import com.crater.android.feature.cash.domain.model.Amount

data class InvoiceInfo(
    val invoiceNo: String,
    val customerName: String,
    val amount: Amount,
    val paymentStatus: String,
    val dueDate: String
)