package com.crater.android.data.dto.transaction.initiate_collect


import com.google.gson.annotations.SerializedName

data class CollectRequestResponse(
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