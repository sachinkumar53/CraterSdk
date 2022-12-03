package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GenerateSdkTokenResponse(
    @SerializedName("expires_at") val expires_at: String,
    @SerializedName("sdk_token") val sdk_token: String,
)