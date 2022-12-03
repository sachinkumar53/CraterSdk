package com.crater.android.feature.kyc.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class KycResult(
    @SerializedName("idNumber")
    val idNumber: String,
    @SerializedName("idStatus")
    val idStatus: String,
    @SerializedName("name")
    val name: String
)