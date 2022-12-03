package com.crater.android.data.mapper

import com.crater.android.data.model.social.InstagramProfileData
import com.crater.android.data.model.social.YouTubeProfileData
import com.crater.android.data.dto.phyllo.GetProfileResponse
import com.crater.android.utils.FormatterUtil

fun GetProfileResponse.toInstagramProfileData(): InstagramProfileData {
    return InstagramProfileData(
        workPlatformName = workPlatform.name,
        workPlatformLogoUrl = workPlatform.logo_url,
        platformUserName = platformUsername,
        contentCount = FormatterUtil.formatQuantity(reputation.content_count),
        followerCount = FormatterUtil.formatQuantity(reputation.follower_count),
        followingCount = FormatterUtil.formatQuantity(reputation.following_count)
    )
}

fun GetProfileResponse.toYouTubeProfileData(): YouTubeProfileData {
    return YouTubeProfileData(
        workPlatformName = workPlatform.name,
        workPlatformLogoUrl = workPlatform.logo_url,
        platformUserName = platformUsername,
        contentCount = FormatterUtil.getFormattedNumber(reputation.content_count),
        subscriberCount = FormatterUtil.getFormattedNumber(reputation.subscriber_count),
        watchTime = FormatterUtil.formatMinutes(reputation.watch_time_in_hours?.toDouble())
    )
}