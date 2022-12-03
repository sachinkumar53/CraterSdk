package com.crater.android.data.dto.engagement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EngagementResponse(
    @SerializedName("data")
    val `data`: List<Data>?
)