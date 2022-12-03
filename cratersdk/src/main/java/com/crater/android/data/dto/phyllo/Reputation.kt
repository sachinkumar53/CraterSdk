package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Reputation(
    @SerializedName("content_count") val content_count: Int?,
    @SerializedName("content_group_count") val content_group_count: Int?,
    @SerializedName("follower_count") val follower_count: Int?,
    @SerializedName("following_count") val following_count: Int?,
    @SerializedName("subscriber_count") val subscriber_count: Int?,
    @SerializedName("watch_time_in_hours") val watch_time_in_hours: Int?,
)