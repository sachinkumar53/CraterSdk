package com.crater.android.model

import com.google.gson.annotations.SerializedName

data class RecordPaymentHistory(
    @SerializedName("amount")
    val amount: String,
    @SerializedName("date")
    val date: String
)