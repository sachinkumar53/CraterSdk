package com.crater.android.data.dto.invoice

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ServiceDto(
    @SerializedName("SAC") val SAC: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("notes") val notes: String?,
    @SerializedName("price") val price: String?,
    @SerializedName("quantity") val quantity: String?,
    @SerializedName("total") val total: String?,
    @SerializedName("amount") val amount: String?
)