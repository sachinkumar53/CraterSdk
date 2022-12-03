package com.crater.android.data.dto.transaction.get_statement


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Statement(
    @SerializedName("timestamp")
    val timestamp: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("bankReferenceNumber")
    val bankReferenceNumber: String,
    @SerializedName("decentroUrn")
    val decentroUrn: String,
    @SerializedName("transferType")
    val transferType: String,
    @SerializedName("withdrawalAmount")
    val withdrawalAmount: Double,
    @SerializedName("depositAmount")
    val depositAmount: Double,
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("type")
    val type: String,
    @SerializedName("senderAccountNumber", alternate = ["recieverAccountNumber"])
    val accountNumber: String,
    @SerializedName("payerVpa")
    val payerVpa: String?,
    @SerializedName("transactionType")
    val transactionType: String,
    @SerializedName("moneyTransferStatus")
    val moneyTransferStatus: String
)