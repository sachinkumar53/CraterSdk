package com.crater.android.feature.kyc.data.dto.error


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Error(
    @SerializedName("message")
    val message: String,
    @SerializedName("responseCode")
    val responseCode: String
)