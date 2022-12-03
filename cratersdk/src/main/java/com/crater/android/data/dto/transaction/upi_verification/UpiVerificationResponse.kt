package com.crater.android.data.dto.transaction.upi_verification


import com.google.gson.annotations.SerializedName

data class UpiVerificationResponse(
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)