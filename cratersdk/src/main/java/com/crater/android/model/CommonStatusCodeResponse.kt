package com.crater.android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CommonStatusCodeResponse(
    @SerializedName("message", alternate = ["detail"]) val message: String,
    @SerializedName("status_code") val code: Int,
    @SerializedName("decentro_id") val deCentroId: String?
) : Serializable