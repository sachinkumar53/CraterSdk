package com.crater.android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommonCodeResponse(
    @SerializedName("message") val message: String,
    @SerializedName("code", alternate = ["status_code"]) val code: Int
) : Serializable