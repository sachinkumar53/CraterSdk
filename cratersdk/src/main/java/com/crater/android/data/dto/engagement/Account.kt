package com.crater.android.data.dto.engagement


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