package com.crater.android.data.dto.pan


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PanVerificationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: Message
)