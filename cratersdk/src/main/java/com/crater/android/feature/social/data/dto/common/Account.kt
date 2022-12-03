package com.crater.android.feature.social.data.dto.common


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Account(
    @SerializedName("id")
    val id: String,
    @SerializedName("platform_username")
    val platformUsername: String,
    @SerializedName("username")
    val username: String
)