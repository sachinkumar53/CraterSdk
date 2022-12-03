package com.crater.android.model

import com.google.gson.annotations.SerializedName

data class LoginSuccessResponse(
    @SerializedName("msg") val message: String,
    @SerializedName("status_code") val code: Int,
    @SerializedName("token") val token: RegistrationTokenResponse,
)

data class RegistrationTokenResponse(
    @SerializedName("access_token") val accessToken: String
)