package com.crater.android.data.dto.transaction.get_balance


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GetBalanceResponse(
    @SerializedName("decentroTxnId")
    val decentroTxnId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("presentBalance")
    val presentBalance: Double,
    @SerializedName("upiId")
    val upiId: String,
    @SerializedName("transactionAmountLimit")
    val transactionAmountLimit: Double,
    @SerializedName("minimumBalance")
    val minimumBalance: Double
)