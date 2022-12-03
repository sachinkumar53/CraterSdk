package com.crater.android.data.dto.phyllo

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GetProfileResponse(
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
    @SerializedName("username")
    val username: String,
    @SerializedName("platform_username")
    val platformUsername: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("nick_name")
    val nickName: Any?,
    @SerializedName("url")
    val url: String,
    @SerializedName("introduction")
    val introduction: Any?,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("date_of_birth")
    val dateOfBirth: String,
    @SerializedName("external_id")
    val externalId: String,
    @SerializedName("platform_account_type")
    val platformAccountType: Any?,
    @SerializedName("category")
    val category: Any?,
    @SerializedName("website")
    val website: Any?,
    @SerializedName("reputation")
    val reputation: Reputation,
    @SerializedName("emails")
    val emails: List<Email>,
    @SerializedName("phone_numbers")
    val phoneNumbers: List<PhoneNumber>,
    @SerializedName("addresses")
    val addresses: List<Any>,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("country")
    val country: Any?,
    @SerializedName("platform_profile_name")
    val platformProfileName: String,
    @SerializedName("platform_profile_id")
    val platformProfileId: String,
    @SerializedName("platform_profile_published_at")
    val platformProfilePublishedAt: String,
    @SerializedName("is_verified")
    val isVerified: Boolean,
    @SerializedName("is_business")
    val isBusiness: Boolean
)

data class Account(
    @SerializedName("id") val id: String?,
    @SerializedName("platform_username") val platform_username: String,
    @SerializedName("username") val username: String,
)

data class PhoneNumber(
    @SerializedName("phone_number") val phone_number: String,
    @SerializedName("type") val type: String,
)
