package com.crater.android.feature.social.data.dto.comment


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CommentResponse(
    @SerializedName("data")
    val `data`: List<Data>
)