package com.crater.android.data.model.social

import com.crater.android.feature.social.domain.model.Comment

data class YoutubeVideo(
    val likeCount: String,
    val dislikeCount: String,
    val commentCount: String,
    val viewCount: String,
    val watchTimeInHours: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val comment: Comment?
)

data class InstagramPost(
    val likeCount: String,
    val dislikeCount: String,
    val commentCount: String,
    val shareCount: String,
    val saveCount: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
    val publishedAt: String,
    val comment: Comment?
)