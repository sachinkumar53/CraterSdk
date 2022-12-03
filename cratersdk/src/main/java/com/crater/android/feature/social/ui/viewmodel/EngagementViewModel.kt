package com.crater.android.feature.social.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.model.UiState
import com.crater.android.data.model.social.InstagramPost
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.data.model.social.YoutubeVideo
import com.crater.android.feature.social.domain.repository.SocialMediaRepository
import com.crater.android.utils.FormatterUtil
import com.crater.android.utils.FormatterUtil.getFormattedNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EngagementViewModel @Inject constructor(
    private val repository: SocialMediaRepository
) : ViewModel() {

    var instagramEngagementState by mutableStateOf<UiState<List<InstagramPost>>>(UiState.Empty())
    var youtubeEngagementState by mutableStateOf<UiState<List<YoutubeVideo>>>(UiState.Empty())

    fun loadData(socialMediaType: SocialMediaType) {
        when (socialMediaType) {
            SocialMediaType.Instagram -> loadPosts()
            SocialMediaType.YouTube -> loadVideos()
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            instagramEngagementState = UiState.Loading()
            when (val response = repository.getInstagramEngagement(
                fromDate = LocalDate.parse("2000-01-01")
            )) {
                is Resource.Success -> {
                    val data = response.data.data

                    if (data == null) {
                        instagramEngagementState = UiState.Empty()
                        return@launch
                    }

                    val commentList = data.associate {
                        it.id to async {
                            repository.getCommentForPost(SocialMediaType.Instagram, it.id)
                        }
                    }.mapValues {
                        it.value.await()
                    }

                    val posts = data.map {
                        val commentResource = commentList[it.id]
                        val engagement = it.engagement
                        InstagramPost(
                            likeCount = getFormattedNumber(engagement.likeCount),
                            dislikeCount = getFormattedNumber(engagement.dislikeCount),
                            commentCount = getFormattedNumber(engagement.commentCount),
                            saveCount = getFormattedNumber(engagement.saveCount),
                            shareCount = getFormattedNumber(engagement.shareCount),
                            title = it.title,
                            url = it.url,
                            thumbnailUrl = it.thumbnailUrl,
                            publishedAt = LocalDateTime.parse(it.publishedAt).format(
                                DateTimeFormatter.ofPattern(DATE_FORMAT)
                            ),
                            comment = if (commentResource != null && commentResource is Resource.Success) {
                                commentResource.data
                            } else null
                        )
                    }
                    instagramEngagementState = UiState.Success(posts)
                }

                is Resource.Error -> {
                    instagramEngagementState = UiState.Error()
                }

                is Resource.Loading -> Unit
            }
        }
    }

    private fun loadVideos() {
        viewModelScope.launch {
            youtubeEngagementState = UiState.Loading()
            when (val response = repository.getYoutubeEngagement(
                fromDate = LocalDate.parse("2000-01-01")
            )) {
                is Resource.Success -> {
                    val data = response.data.data

                    if (data == null) {
                        youtubeEngagementState = UiState.Empty()
                        return@launch
                    }

                    val commentList = data.associate {
                        it.id to async {
                            repository.getCommentForPost(SocialMediaType.YouTube, it.id)
                        }
                    }.mapValues {
                        it.value.await()
                    }

                    val videos = data.map {
                        val commentResource = commentList[it.id]
                        val engagement = it.engagement
                        YoutubeVideo(
                            likeCount = getFormattedNumber(engagement.likeCount ?: 0),
                            dislikeCount = getFormattedNumber(engagement.dislikeCount ?: 0),
                            commentCount = getFormattedNumber(engagement.commentCount ?: 0),
                            viewCount = getFormattedNumber(engagement.viewCount ?: 0),
                            watchTimeInHours = FormatterUtil.formatHours(engagement.watchTimeInHours),
                            title = it.title,
                            url = it.url,
                            thumbnailUrl = it.thumbnailUrl,
                            publishedAt = LocalDateTime.parse(it.publishedAt)
                                .format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                            comment = if (commentResource != null && commentResource is Resource.Success) {
                                commentResource.data
                            } else null
                        )
                    }
                    youtubeEngagementState = UiState.Success(videos)
                }

                is Resource.Error -> {
                    youtubeEngagementState = UiState.Error()
                }

                is Resource.Loading -> Unit
            }
        }
    }

    companion object {
        private const val DATE_FORMAT = "dd MMMM yyyy"
    }
}