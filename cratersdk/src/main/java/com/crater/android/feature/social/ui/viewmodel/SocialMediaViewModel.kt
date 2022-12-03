package com.crater.android.feature.social.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.crater.android.R
import com.crater.android.core.data.Resource
import com.crater.android.core.ui.event.EventHandler
import com.crater.android.core.ui.event.UiEvent.ShowToast
import com.crater.android.core.util.UiText.StringResource
import com.crater.android.data.dto.engagement.Data
import com.crater.android.data.mapper.toInstagramProfileData
import com.crater.android.data.mapper.toYouTubeProfileData
import com.crater.android.data.model.social.*
import com.crater.android.feature.social.domain.model.PhylloSdkToken
import com.crater.android.feature.social.domain.model.PhylloUserId
import com.crater.android.feature.social.domain.model.isNullOrEmpty
import com.crater.android.feature.social.domain.repository.SocialMediaRepository
import com.crater.android.utils.BaseViewModel
import com.crater.android.utils.FormatterUtil.getFormattedNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocialMediaViewModel @Inject constructor(
    private val repository: SocialMediaRepository,
    private val eventHandler: EventHandler
) : BaseViewModel() {
    private val _onInitSdkChannel =
        Channel<Triple<PhylloSdkToken, PhylloUserId, String>>(Channel.BUFFERED)
    val onInitSdkFlow = _onInitSdkChannel.receiveAsFlow()

    private var socialMediaType: SocialMediaType? = null

    private val isLoadingInstagramProfile = MutableStateFlow(false)
    private val isLoadingYoutubeProfile = MutableStateFlow(false)
    private val isCreatingUserOrToken = MutableStateFlow(false)

    val isLoadingProfile = combine(
        isLoadingInstagramProfile,
        isLoadingYoutubeProfile,
        isCreatingUserOrToken
    ) { loadingInstagram, loadingYoutube, creatingUserOrToken ->
        loadingInstagram or loadingYoutube or creatingUserOrToken
    }

    private val _isLast28Days = MutableStateFlow(true)
    val isLast28Days = _isLast28Days.asStateFlow()

    private val _instagramProfileData = MutableStateFlow(InstagramProfileData())
    val instagramProfileData = _instagramProfileData.asStateFlow()

    private val _youTubeProfileData = MutableStateFlow(YouTubeProfileData())
    val youTubeProfileData = _youTubeProfileData.asStateFlow()

    private val _isInstagramConnected = MutableStateFlow(false)
    val isInstagramConnected = _isInstagramConnected.asStateFlow()

    private val _isYoutubeConnected = MutableStateFlow(false)
    val isYoutubeConnected = _isYoutubeConnected.asStateFlow()

    var youtubeEngagementSummary: Resource<YoutubeEngagement> by mutableStateOf(Resource.Error())
        private set

    var instagramEngagementSummary: Resource<InstagramEngagement> by mutableStateOf(Resource.Error())
        private set

    /*var instagramPosts: Resource<List<InstagramPost>> by mutableStateOf(Resource.Error())
        private set

    var youtubeVideos: Resource<List<YoutubeVideo>> by mutableStateOf(Resource.Error())
        private set*/

    init {
        repository.getInstagramAccountId().onEach {
            onAccountConnected(SocialMediaType.Instagram, it)
        }.launchIn(viewModelScope)

        repository.getYoutubeAccountId().onEach {
            onAccountConnected(SocialMediaType.YouTube, it)
        }.launchIn(viewModelScope)
    }

    private suspend fun onCreateUser() {
        isCreatingUserOrToken.value = true
        when (val resource = repository.createUser()) {
            is Resource.Success -> {
                val token = repository.getPhylloSdkToken()
                if (token.isNullOrEmpty()) {
                    generateSdkToken()
                } else {
                    sendSdkInitializationEvent(token!!)
                    isCreatingUserOrToken.value = false
                }
            }
            is Resource.Error -> {
                onApiError(resource.throwable)
                isCreatingUserOrToken.value = false
            }
            is Resource.Loading -> {
                isCreatingUserOrToken.value = true
            }
        }

    }


    private suspend fun generateSdkToken() {
        isCreatingUserOrToken.value = true
        when (val resource = repository.generateSdkToken()) {
            is Resource.Success -> {
                val token = resource.data
                if (repository.getPhylloUserId().isNullOrEmpty()) {
                    onCreateUser()
                    return
                }
                isCreatingUserOrToken.value = false
                sendSdkInitializationEvent(token)
            }
            is Resource.Error -> {
                isCreatingUserOrToken.value = false
                onApiError(resource.throwable)
            }
            is Resource.Loading -> {
                isCreatingUserOrToken.value = false
            }
        }

    }

    private fun sendSdkInitializationEvent(token: PhylloSdkToken) {
        val type = this.socialMediaType
        if (type == null) {
            Log.w(
                TAG,
                "sendSdkInitializationEvent: Specify a social media type for the SDK initialization"
            )
            return
        }

        viewModelScope.launch {
            _onInitSdkChannel.send(
                Triple(
                    token,
                    repository.getPhylloUserId()!!,
                    type.workPlatformId
                )
            )
        }
        this.socialMediaType = null
    }

    private suspend fun loadInstagramProfile(
        handleError: Boolean = true
    ) {
        isLoadingInstagramProfile.value = true
        when (val resource = repository.getInstagramProfile()) {
            is Resource.Success -> {
                val accountId = resource.data.account.id
                val sdkToken = repository.getPhylloSdkToken()
                val userId = repository.getPhylloUserId()
                if (!accountId.isNullOrEmpty() && sdkToken.isNullOrEmpty()) {
                    generateSdkToken()
                } else if (!accountId.isNullOrEmpty() && userId.isNullOrEmpty()) {
                    onCreateUser()
                }
                _instagramProfileData.value = resource.data.toInstagramProfileData()
                isLoadingInstagramProfile.value = false
                getInstagramEngagement()
            }
            is Resource.Error -> {
                if (handleError) onApiError(resource.throwable)
                isLoadingInstagramProfile.value = false
            }
            is Resource.Loading -> {
                isLoadingInstagramProfile.value = true
            }
        }
    }


    private suspend fun loadYoutubeProfile(
        handleError: Boolean = true
    ) {
        when (val resource = repository.getYoutubeProfile()) {
            is Resource.Success -> {
                val accountId = resource.data.account.id
                val sdkToken = repository.getPhylloSdkToken()
                val userId = repository.getPhylloUserId()
                if (!accountId.isNullOrEmpty() && sdkToken.isNullOrEmpty()) {
                    generateSdkToken()
                } else if (!accountId.isNullOrEmpty() && userId.isNullOrEmpty()) {
                    onCreateUser()
                }
                _youTubeProfileData.value = resource.data.toYouTubeProfileData()
                isLoadingYoutubeProfile.value = false

                getYoutubeEngagement()
            }
            is Resource.Error -> {
                isLoadingYoutubeProfile.value = false
                if (handleError)
                    onApiError(resource.throwable)
            }
            is Resource.Loading -> {
                isLoadingYoutubeProfile.value = true
            }
        }
    }

    fun disconnectAccount(
        socialMediaType: SocialMediaType,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            when (val resource = repository.disconnectAccount(socialMediaType)) {
                is Resource.Success -> {
                    onSuccess()
                    eventHandler.sendEvent(ShowToast(StringResource(R.string.disconnect_successfully)))
                }
                is Resource.Error -> onApiError(resource.throwable)
                is Resource.Loading -> Unit
            }
        }
    }

    private fun getYoutubeEngagement() {
        viewModelScope.launch {
            youtubeEngagementSummary = Resource.Loading()
            //youtubeVideos = Resource.Loading()

            when (val resource = repository.getYoutubeEngagement()) {
                is Resource.Success -> handleYoutubeVideoResponse(resource.data.data)
                is Resource.Error -> onApiError(resource.throwable)
                is Resource.Loading -> Unit
            }
        }
    }

    private fun getInstagramEngagement() {
        viewModelScope.launch {
            instagramEngagementSummary = Resource.Loading()
            //instagramPosts = Resource.Loading()

            when (val resource = repository.getInstagramEngagement()) {
                is Resource.Success -> handleInstagramResponse(resource.data.data)
                is Resource.Error -> onApiError(resource.throwable)
                is Resource.Loading -> Unit
            }
        }
    }

    private fun handleInstagramResponse(data: List<Data>?) {
        if (data == null) {
            instagramEngagementSummary = Resource.Error(Exception("Data is null"))
            return
        }

        val likeCount = data.map { it.engagement }.sumOf { it.likeCount ?: 0 }
        val commentCount = data.map { it.engagement }.sumOf { it.commentCount ?: 0 }
        val shareCount = data.map { it.engagement }.sumOf { it.shareCount ?: 0 }
        val saveCount = data.map { it.engagement }.sumOf { it.saveCount ?: 0 }

        instagramEngagementSummary = Resource.Success(
            data = InstagramEngagement(
                likeCount = getFormattedNumber(likeCount),
                commentCount = getFormattedNumber(commentCount),
                shareCount = getFormattedNumber(shareCount),
                saveCount = getFormattedNumber(saveCount)
            )
        )
        //loadPosts(data)
    }

    private fun handleYoutubeVideoResponse(
        data: List<Data>?,
    ) {
        if (data == null) {
            youtubeEngagementSummary = Resource.Error(Exception("Data is null"))
            //youtubeVideos = Resource.Error(Exception("Data is null"))
            return
        }

        val engagementList = data.map { it.engagement }
//        val engagement = engagementList.sumOf { it.watchTimeInHours ?: 0.0 }
        val likeCount = engagementList.sumOf { it.likeCount ?: 0 }
        val commentCount = engagementList.sumOf { it.commentCount ?: 0 }
        val viewCount = engagementList.sumOf { it.viewCount ?: 0 }
        //val time = FormatterUtil.formatMinutes(engagement)

        youtubeEngagementSummary = Resource.Success(
            data = YoutubeEngagement(
                //watchTime = time,
                contentLikes = getFormattedNumber(likeCount),
                contentComments = getFormattedNumber(commentCount),
                contentViews = getFormattedNumber(viewCount),

                )
        )
        //loadVideos(engagementList, data)
    }

    /*private fun loadVideos(engagementList: List<Engagement>, data: List<Data>) {
        viewModelScope.launch {
            val commentList = data.associate {
                it.id to async {
                    repository.getCommentForPost(
                        SocialMediaType.YouTube,
                        it.id
                    )
                }
            }.mapValues { it.value.await() }

            youtubeVideos = Resource.Success(
                engagementList.mapIndexed { index, item ->
                    val d = data.getOrNull(index)
                    val comment = d?.id?.let { commentList[it] }
                    YoutubeVideo(
                        likeCount = formatQuantity(item.likeCount),
                        dislikeCount = formatQuantity(item.dislikeCount),
                        commentCount = formatQuantity(item.commentCount),
                        viewCount = formatQuantity(item.viewCount),
                        watchTimeInHours = FormatterUtil.formatHours(item.watchTimeInHours),
                        title = d?.title ?: "",
                        url = d?.url ?: "",
                        thumbnailUrl = d?.thumbnailUrl ?: "",
                        publishedAt = d?.publishedAt?.let {
                            LocalDateTime.parse(it)
                                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                        } ?: "",
                        comment = if (comment != null && comment is Resource.Success) {
                            comment.data
                        } else null
                    )
                }
            )
        }
    }*/

    /*private fun loadPosts(
        data: List<Data>
    ) {
        viewModelScope.launch {
            val commentList = data.associate {
                it.id to async {
                    repository.getCommentForPost(
                        SocialMediaType.Instagram,
                        it.id
                    )
                }
            }.mapValues { it.value.await() }

            instagramPosts = Resource.Success(
                data.mapIndexed { _, item ->
                    val engagement = item.engagement
                    val comment = item.id.let { commentList[it] }

                    InstagramPost(
                        likeCount = getFormattedNumber(engagement.likeCount),
                        dislikeCount = getFormattedNumber(engagement.dislikeCount),
                        commentCount = getFormattedNumber(engagement.commentCount),
                        saveCount = getFormattedNumber(engagement.saveCount),
                        shareCount = getFormattedNumber(engagement.shareCount),
                        title = item.title,
                        url = item.url,
                        thumbnailUrl = item.thumbnailUrl,
                        publishedAt = item.publishedAt.let {
                            LocalDateTime.parse(it)
                                .format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                        } ?: "",
                        comment = if (comment != null && comment is Resource.Success) {
                            comment.data
                        } else null
                    )
                }
            )
        }
    }*/

    fun onAccountConnected(
        socialMediaType: SocialMediaType,
        accountId: String?
    ) {
        viewModelScope.launch {
            when (socialMediaType) {
                SocialMediaType.Instagram -> {
                    //Handle the error only if the user is logged in
                    //Otherwise getting an error is obvious and can be ignored
                    loadInstagramProfile(accountId != null)
                    _isInstagramConnected.update { accountId != null }
                }
                SocialMediaType.YouTube -> {
                    loadYoutubeProfile(accountId != null)
                    _isYoutubeConnected.update { accountId != null }
                }
            }
        }
    }


    fun onInstagramClick(): Boolean {
        return when {
            repository.getPhylloUserId().isNullOrEmpty() -> {
                socialMediaType = SocialMediaType.Instagram
                viewModelScope.launch {
                    onCreateUser()
                }
                false
            }

            repository.getPhylloSdkToken().isNullOrEmpty() || repository.getInstagramAccountIdSync()
                .isNullOrEmpty() -> {
                socialMediaType = SocialMediaType.Instagram
                viewModelScope.launch {
                    generateSdkToken()
                }
                false
            }

            !repository.getPhylloSdkToken().isNullOrEmpty() && !isInstagramConnected.value -> {
                socialMediaType = SocialMediaType.Instagram
                sendSdkInitializationEvent(repository.getPhylloSdkToken()!!)
                false
            }

            !repository.getInstagramAccountIdSync().isNullOrEmpty() -> {
                true
            }
            else -> false
        }
    }

    fun onYoutubeClick(): Boolean {
        return when {
            repository.getPhylloUserId().isNullOrEmpty() -> {
                socialMediaType = SocialMediaType.YouTube
                viewModelScope.launch {
                    onCreateUser()
                }
                false
            }
            repository.getPhylloSdkToken().isNullOrEmpty() || repository.getYoutubeAccountIdSync()
                .isNullOrEmpty() -> {
                socialMediaType = SocialMediaType.YouTube
                viewModelScope.launch {
                    generateSdkToken()
                }
                false
            }

            !repository.getPhylloSdkToken().isNullOrEmpty() && !isYoutubeConnected.value -> {
                socialMediaType = SocialMediaType.YouTube
                sendSdkInitializationEvent(repository.getPhylloSdkToken()!!)
                false
            }

            !repository.getYoutubeAccountIdSync().isNullOrEmpty() -> {
                true
            }
            else -> false
        }
    }

    /*fun onLastDayChanged(is28days: Boolean) {
        _isLast28Days.value = is28days
        viewModelScope.launch {
            launch { loadInstagramProfile(handleError = false) }
            launch { loadYoutubeProfile(handleError = false) }
        }
    }*/
}

private const val TAG = "SocialMediaViewModel"
