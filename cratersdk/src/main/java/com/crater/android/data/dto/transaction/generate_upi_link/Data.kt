package com.crater.android.data.dto.transaction.generate_upi_link


import com.google.gson.annotations.SerializedName

data class Data(
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