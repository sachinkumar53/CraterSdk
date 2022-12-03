package com.crater.android.feature.social.data.dto.youtube


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engagement(
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("dislike_count")
    val dislikeCount: Any?,
    @SerializedName("comment_count")
    val commentCount: Int,
    @SerializedName("impression_organic_count")
    val impressionOrganicCount: Int,
    @SerializedName("reach_organic_count")
    val reachOrganicCount: Int,
    @SerializedName("save_count")
    val saveCount: Int,
    @SerializedName("view_count")
    val viewCount: Any?,
    @SerializedName("watch_time_in_hours")
    val watchTimeInHours: Any?,
    @SerializedName("share_count")
    val shareCount: Any?,
    @SerializedName("impression_paid_count")
    val impressionPaidCount: Any?,
    @SerializedName("reach_paid_count")
    val reachPaidCount: Any?
)