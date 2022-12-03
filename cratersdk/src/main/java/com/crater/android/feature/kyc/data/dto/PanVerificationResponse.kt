package com.crater.android.feature.kyc.data.dto


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PanVerificationResponse(
    @SerializedName("message")
    val message: Message
)