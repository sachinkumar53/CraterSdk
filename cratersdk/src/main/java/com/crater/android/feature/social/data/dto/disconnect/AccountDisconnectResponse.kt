package com.crater.android.feature.social.data.dto.disconnect


import androidx.annotation.Keep
import com.crater.android.feature.social.data.dto.common.User
import com.crater.android.feature.social.data.dto.common.WorkPlatform
import com.google.gson.annotations.SerializedName

@Keep
data class AccountDisconnectResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("work_platform")
    val workPlatform: WorkPlatform,
    @SerializedName("status")
    val status: String
)