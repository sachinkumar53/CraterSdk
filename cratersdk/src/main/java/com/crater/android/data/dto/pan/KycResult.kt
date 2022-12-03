package com.crater.android.data.dto.pan


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