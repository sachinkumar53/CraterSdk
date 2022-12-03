package com.crater.android.data.dto.transaction.create_account


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("bank")
    val bank: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("ifsc")
    val ifsc: String,
    @SerializedName("allowedMethods")
    val allowedMethods: List<String>,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("transactionLimit")
    val transactionLimit: Int,
    @SerializedName("minimumBalance")
    val minimumBalance: Int,
    @SerializedName("upiId")
    val upiId: String,
    @SerializedName("upiQrCode")
    val upiQrCode: String,
    @SerializedName("upiOnboardingStatus")
    val upiOnboardingStatus: String,
    @SerializedName("upiOnboardingStatusDescription")
    val upiOnboardingStatusDescription: String,
    @SerializedName("upiOnboardingDetails")
    val upiOnboardingDetails: UpiOnboardingDetails,
    @SerializedName("virtualAccountBalanceSettlement")
    val virtualAccountBalanceSettlement: String
)