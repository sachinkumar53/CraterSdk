package com.crater.android.data.dto.transaction.initiate_collect


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("transactionId")
    val transactionId: String,
    @SerializedName("transactionStatus")
    val transactionStatus: String,
    @SerializedName("transactionStatusDescription")
    val transactionStatusDescription: String,
    @SerializedName("bankReferenceNumber")
    val bankReferenceNumber: String,
    @SerializedName("npciTransactionId")
    val npciTransactionId: String
)