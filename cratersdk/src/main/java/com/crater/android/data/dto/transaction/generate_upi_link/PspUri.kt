package com.crater.android.data.dto.transaction.generate_upi_link


import com.google.gson.annotations.SerializedName

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