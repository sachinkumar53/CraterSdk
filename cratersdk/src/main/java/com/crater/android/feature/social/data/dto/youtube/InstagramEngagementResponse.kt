package com.crater.android.feature.social.data.dto.youtube


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class InstagramEngagementResponse(
    @SerializedName("data")
    val `data`: List<Data>
)