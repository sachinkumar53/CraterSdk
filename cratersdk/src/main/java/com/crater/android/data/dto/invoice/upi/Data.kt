package com.crater.android.data.dto.invoice.upi


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Data(
    @SerializedName("encodedDynamicQrCode")
    val encodedDynamicQrCode: String,
    @SerializedName("upiUri")
    val upiUri: String,
    @SerializedName("pspUri")
    val pspUri: PspUri,
    @SerializedName("generatedLink")
    val generatedLink: String,
    @SerializedName("transactionId")
    val transactionId: String,
    @SerializedName("transactionStatus")
    val transactionStatus: String
)