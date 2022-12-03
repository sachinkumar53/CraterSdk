package com.crater.android.feature.social.data.dto.comment


import androidx.annotation.Keep
import com.crater.android.feature.social.data.dto.common.Account
import com.crater.android.feature.social.data.dto.common.User
import com.crater.android.feature.social.data.dto.common.WorkPlatform
import com.google.gson.annotations.SerializedName

@Keep
data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("account")
    val account: Account,
    @SerializedName("work_platform")
    val workPlatform: WorkPlatform,
    @SerializedName("content")
    val content: Content,
    @SerializedName("external_id")
    val externalId: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("commenter_username")
    val commenterUsername: String,
    @SerializedName("commenter_display_name")
    val commenterDisplayName: String,
    @SerializedName("commenter_id")
    val commenterId: String,
    @SerializedName("commenter_profile_url")
    val commenterProfileUrl: String?,
    @SerializedName("like_count")
    val likeCount: Int,
    @SerializedName("reply_count")
    val replyCount: Int
)