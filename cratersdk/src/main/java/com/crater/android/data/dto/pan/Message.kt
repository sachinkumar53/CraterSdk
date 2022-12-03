package com.crater.android.data.dto.pan


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
    val kycResult: KycResult,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("requestTimestamp")
    val requestTimestamp: String,
    @SerializedName("responseTimestamp")
    val responseTimestamp: String,
    @SerializedName("decentroTxnId")
    val decentroTxnId: String
)