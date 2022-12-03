package com.crater.android.feature.kyc.data.dto.error


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Detail(
    @SerializedName("status")
    val status: String,
    @SerializedName("error")
    val error: Error,
    @SerializedName("requestTimestamp")
    val requestTimestamp: String,
    @SerializedName("responseTimestamp")
    val responseTimestamp: String,
    @SerializedName("decentroTxnId")
    val decentroTxnId: String
)