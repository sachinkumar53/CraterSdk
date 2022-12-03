package com.crater.android.data.dto.transaction.create_account


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UpiOnboardingDetails(
    @SerializedName("merchantCategoryCode")
    val merchantCategoryCode: String,
    @SerializedName("merchantBusinessType")
    val merchantBusinessType: String,
    @SerializedName("transactionCountLimitPerDay")
    val transactionCountLimitPerDay: Int,
    @SerializedName("transactionAmountLimitPerDay")
    val transactionAmountLimitPerDay: Int,
    @SerializedName("transactionAmountLimitPerTransaction")
    val transactionAmountLimitPerTransaction: Int
)