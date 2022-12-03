package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Email(
    @SerializedName("email_id") val email_id: String,
    @SerializedName("type") val type: String,
)