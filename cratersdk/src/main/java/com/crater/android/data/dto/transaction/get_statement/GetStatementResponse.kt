package com.crater.android.data.dto.transaction.get_statement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GetStatementResponse(
    @SerializedName("accountNumber")
    val accountNumber: String,
    @SerializedName("ifsc")
    val ifsc: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("from")
    val from: String,
    @SerializedName("to")
    val to: String,
    @SerializedName("decentroTxnId")
    val decentroTxnId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("responseCode")
    val responseCode: String,
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("withdrawalCount")
    val withdrawalCount: Int,
    @SerializedName("depositCount")
    val depositCount: Int,
    @SerializedName("openingBalance")
    val openingBalance: Double,
    @SerializedName("closingBalance")
    val closingBalance: Double,
    @SerializedName("statement")
    val statement: List<Statement>
)