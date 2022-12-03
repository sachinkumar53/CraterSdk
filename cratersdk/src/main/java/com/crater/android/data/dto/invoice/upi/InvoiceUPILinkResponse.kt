package com.crater.android.data.dto.invoice.upi


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class InvoiceUPILinkResponse(
    @SerializedName("decentroTxnId")
    val decentroTxnId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val `data`: Data
)