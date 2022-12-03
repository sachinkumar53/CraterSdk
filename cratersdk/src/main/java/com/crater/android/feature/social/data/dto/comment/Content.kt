package com.crater.android.feature.social.data.dto.comment


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Content(
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("published_at")
    val publishedAt: String
)