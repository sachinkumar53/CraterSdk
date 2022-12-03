package com.crater.android.feature.invoicing.domain.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.crater.android.R
import com.crater.android.feature.cash.domain.model.Amount

sealed interface Tracker {
    val count: Int
    val totalAmount: Amount
    val amountDue: Amount

    data class InvoiceTracker(
        override val count: Int,
        override val totalAmount: Amount,
        override val amountDue: Amount
    ) : Tracker

    data class PaymentTracker(
        override val count: Int,
        override val totalAmount: Amount,
        override val amountDue: Amount
    ) : Tracker


    @Composable
    fun asString(): String {
        return stringResource(R.string.tracker_text_format, count, totalAmount.displayValue)
    }
}
