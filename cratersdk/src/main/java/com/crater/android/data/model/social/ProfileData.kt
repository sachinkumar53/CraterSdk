package com.crater.android.data.model.social

sealed interface ProfileData {
    val workPlatformName: String?
    val workPlatformLogoUrl: String?
    val platformUserName: String?
}

data class InstagramProfileData(
    override val workPlatformName: String? = null,
    override val workPlatformLogoUrl: String? = null,
    override val platformUserName: String? = null,
    val contentCount: String? = null,
    val followerCount: String? = null,
    val followingCount: String? = null
) : ProfileData

data class YouTubeProfileData(
    override val workPlatformName: String? = null,
    override val workPlatformLogoUrl: String? = null,
    override val platformUserName: String? = null,
    val subscriberCount: String? = null,
    val contentCount: String? = null,
    val watchTime: String? = null
) : ProfileData
