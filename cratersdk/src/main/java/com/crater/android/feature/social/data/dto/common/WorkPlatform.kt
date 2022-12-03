package com.crater.android.feature.social.data.dto.common


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class WorkPlatform(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("logo_url")
    val logoUrl: String
)