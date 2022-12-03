package com.crater.android.feature.kyc.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Message(
    @SerializedName("kycStatus")
    val kycStatus: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("kycResult")
    val kycResult: KycResult
    )