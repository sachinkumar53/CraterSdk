package com.crater.android.data.dto.transaction.create_account


import com.google.gson.annotations.SerializedName

data class CreateVirtualAccountResponse(
    @SerializedName("decentroTxnId")
    val decentroTxnId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val `data`: List<Data>
)