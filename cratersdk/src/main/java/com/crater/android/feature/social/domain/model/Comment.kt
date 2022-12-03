package com.crater.android.feature.social.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Comment(
    val profilePicUrl: String?,
    val userName: String,
    val commentDate: String,
    val text: String
)
