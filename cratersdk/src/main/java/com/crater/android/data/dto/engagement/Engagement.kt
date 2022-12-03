package com.crater.android.data.dto.engagement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engagement(
    @SerializedName("like_count")
    val likeCount: Int?,
    @SerializedName("dislike_count")
    val dislikeCount: Int?,
    @SerializedName("comment_count")
    val commentCount: Int?,
    @SerializedName("impression_organic_count")
    val impressionOrganicCount: Any?,
    @SerializedName("reach_organic_count")
    val reachOrganicCount: Any?,
    @SerializedName("save_count")
    val saveCount: Int?,
    @SerializedName("view_count")
    val viewCount: Int?,
    @SerializedName("watch_time_in_hours")
    val watchTimeInHours: Double?,
    @SerializedName("share_count")
    val shareCount: Int?,
    @SerializedName("impression_paid_count")
    val impressionPaidCount: Any?,
    @SerializedName("reach_paid_count")
    val reachPaidCount: Any?
)