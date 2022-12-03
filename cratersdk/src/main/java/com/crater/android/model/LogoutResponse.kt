package com.crater.android.model

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("access_token")
    val access_token: AccessToken,
    @SerializedName("message")
    val message: String,
    @SerializedName("token_type")
    val token_type: String,
)

data class AccessToken(
    @SerializedName("access_token")
    val access_token: String,
)