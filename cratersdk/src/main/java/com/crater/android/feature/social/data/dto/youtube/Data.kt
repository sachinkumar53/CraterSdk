package com.crater.android.feature.social.data.dto.youtube


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import com.crater.android.feature.social.data.dto.common.Account
import com.crater.android.feature.social.data.dto.common.User
import com.crater.android.feature.social.data.dto.common.WorkPlatform

@Keep
data class Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("account")
    val account: Account,
    @SerializedName("work_platform")
    val workPlatform: WorkPlatform,
    @SerializedName("engagement")
    val engagement: Engagement,
    @SerializedName("external_id")
    val externalId: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("format")
    val format: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("media_url")
    val mediaUrl: String,
    @SerializedName("duration")
    val duration: Any?,
    @SerializedName("description")
    val description: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("platform_profile_id")
    val platformProfileId: String,
    @SerializedName("platform_profile_name")
    val platformProfileName: String,
    @SerializedName("sponsored")
    val sponsored: Any?,
    @SerializedName("collaboration")
    val collaboration: Any?,
    @SerializedName("is_owned_by_platform_user")
    val isOwnedByPlatformUser: Boolean
)