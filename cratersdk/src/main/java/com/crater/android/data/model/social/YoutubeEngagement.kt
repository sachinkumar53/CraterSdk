package com.crater.android.data.model.social

data class YoutubeEngagement(
    val contentLikes: String,
    val contentComments: String,
    val contentViews: String,
)

data class InstagramEngagement(
    val likeCount: String,
    val commentCount: String,
    val shareCount: String,
    val saveCount: String
)