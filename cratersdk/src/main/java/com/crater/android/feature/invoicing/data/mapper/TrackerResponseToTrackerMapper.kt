package com.crater.android.feature.invoicing.data.mapper

import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.invoicing.domain.model.Tracker.InvoiceTracker
import com.crater.android.feature.invoicing.domain.model.Tracker.PaymentTracker
import com.crater.android.model.PendingPaymentResponse

fun PendingPaymentResponse.toPaymentTracker(): PaymentTracker {
    return PaymentTracker(
        count = count,
        totalAmount = Amount(totalAmount),
        amountDue = Amount(amountDue)
    )
}

fun PendingPaymentResponse.toInvoiceTracker(): InvoiceTracker {
    return InvoiceTracker(
        count = count,
        totalAmount = Amount(totalAmount),
        amountDue = Amount(amountDue)
    )
}