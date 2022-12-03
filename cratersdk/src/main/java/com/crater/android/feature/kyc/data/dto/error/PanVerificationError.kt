package com.crater.android.feature.kyc.data.dto.error


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PanVerificationError(
    @SerializedName("detail")
    val detail: Detail
)