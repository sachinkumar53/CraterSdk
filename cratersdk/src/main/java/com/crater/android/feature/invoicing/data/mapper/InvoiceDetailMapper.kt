package com.crater.android.feature.invoicing.data.mapper

import com.crater.android.data.dto.invoice.InvoiceDetailResponse
import com.crater.android.feature.invoicing.domain.model.InvoiceInfo
import com.crater.android.feature.cash.domain.model.Amount

fun InvoiceDetailResponse.toInfo(): InvoiceInfo {
    return InvoiceInfo(
        invoiceNo = invoiceNo,
        customerName = customer,
        amount = Amount(amount.toDoubleOrNull() ?: 0.0),
        paymentStatus = status,
        dueDate = dueDate
    )
}