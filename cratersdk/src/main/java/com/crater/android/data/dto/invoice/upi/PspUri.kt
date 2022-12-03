package com.crater.android.data.dto.invoice.upi


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PspUri(
    @SerializedName("commonUri")
    val commonUri: String,
    @SerializedName("gpayUri")
    val gpayUri: String,
    @SerializedName("phonepeUri")
    val phonepeUri: String,
    @SerializedName("paytmUri")
    val paytmUri: String
)