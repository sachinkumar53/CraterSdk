package com.crater.android.model

import com.google.gson.annotations.SerializedName

data class PendingPaymentResponse(
    @SerializedName("amount_due") val amountDue: Double,
    @SerializedName("total_amount") val totalAmount: Double,
    @SerializedName("count") val count: Int
)