package com.crater.android.data.dto.transaction.self_transfer


import com.google.gson.annotations.SerializedName

data class SendToSelfResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("decentroTxnId")
    val decentroTxnId: String,
    @SerializedName("transferType")
    val transferType: String,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("newBalance")
    val newBalance: Int
)