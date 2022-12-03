package com.crater.android.feature.social.data.repository

import android.text.format.DateUtils
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.Resource
import com.crater.android.data.dto.engagement.EngagementResponse
import com.crater.android.data.dto.phyllo.CreateUserResponse
import com.crater.android.data.dto.phyllo.GetProfileResponse
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.data.network.ApiService
import com.crater.android.feature.social.data.dto.disconnect.AccountDisconnectResponse
import com.crater.android.feature.social.domain.model.Comment
import com.crater.android.feature.social.domain.model.PhylloSdkToken
import com.crater.android.feature.social.domain.model.PhylloUserId
import com.crater.android.feature.social.domain.repository.SocialMediaRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

class SocialMediaRepositoryImpl(
    private val cacheManager: CacheManager,
    private val apiService: ApiService
) : SocialMediaRepository {

    override fun getInstagramAccountId(): Flow<String?> {
        return cacheManager.getInstagramAccountId().distinctUntilChanged()
    }

    override fun getYoutubeAccountId(): Flow<String?> {
        return cacheManager.getYoutubeAccountId().distinctUntilChanged()
    }

    override fun getInstagramAccountIdSync(): String? = runBlocking {
        cacheManager.getInstagramAccountId().firstOrNull()
    }

    override fun getYoutubeAccountIdSync(): String? = runBlocking {
        cacheManager.getYoutubeAccountId().firstOrNull()
    }

    override fun getPhylloSdkToken(): PhylloSdkToken? {
        return runBlocking {
            cacheManager.getPhylloSdkToken().firstOrNull()?.let { PhylloSdkToken(it) }
        }
    }

    override fun getPhylloUserId(): PhylloUserId? {
        return runBlocking {
            cacheManager.getPhylloUserId().firstOrNull()?.let { PhylloUserId(it) }
        }
    }

    override suspend fun createUser(): Resource<CreateUserResponse> =
        withContext(Dispatchers.Default) {
            try {
                val response = apiService.createPhylloUser()
                response.output.phyllo_id?.let {
                    cacheManager.setPhylloUserId(it)
                }
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun generateSdkToken(): Resource<PhylloSdkToken> =
        withContext(Dispatchers.Default) {
            try {
                val response = apiService.generatePhylloSdkToken()
                response.sdk_token.let {
                    cacheManager.setPhylloSdkToken(it)
                }
                Resource.Success(PhylloSdkToken(response.sdk_token))
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun getYoutubeProfile(): Resource<GetProfileResponse> =
        withContext(Dispatchers.Default) {
            try {
                val response = apiService.getYoutubeProfile()
                response.account.id?.let {
                    cacheManager.setYoutubeAccountId(it)
                }
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun getInstagramProfile(): Resource<GetProfileResponse> =
        withContext(Dispatchers.Default) {
            try {
                val response = apiService.getInstagramProfile()
                response.account.id?.let {
                    cacheManager.setInstagramAccountId(it)
                }
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        }

    override suspend fun getYoutubeEngagement(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Resource<EngagementResponse> {
        return withContext(Dispatchers.Default) {
            try {
                val response = apiService.getYoutubeEngagements(
                    fromDate = fromDate.toString(),
                    toDate = toDate.toString()
                )
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(throwable = e)
            }
        }
    }

    override suspend fun getInstagramEngagement(
        fromDate: LocalDate,
        toDate: LocalDate
    ): Resource<EngagementResponse> {
        return withContext(Dispatchers.Default) {
            try {
                val response =
                    apiService.getInstagramEngagements(
                        fromDate = fromDate.toString(),
                        toDate = toDate.toString()
                    )
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(throwable = e)
            }
        }
    }

    override suspend fun disconnectAccount(
        socialMediaType: SocialMediaType
    ): Resource<AccountDisconnectResponse> = withContext(Dispatchers.Default) {

        val accountId = when (socialMediaType) {
            SocialMediaType.Instagram -> cacheManager.getInstagramAccountId().firstOrNull()
            SocialMediaType.YouTube -> cacheManager.getYoutubeAccountId().firstOrNull()
        }

        if (accountId != null) {
            try {
                val response = apiService.disconnectPhylloAccount(accountId)
                if (response.status.equals("NOT_CONNECTED", true)) {
                    when (socialMediaType) {
                        SocialMediaType.Instagram -> cacheManager.removeInstagramAccountId()
                        SocialMediaType.YouTube -> cacheManager.removeYoutubeAccountId()
                    }
                }
                Resource.Success(response)
            } catch (e: Exception) {
                Resource.Error(e)
            }
        } else {
            Resource.Error(
                Exception("Account id is null: Account Id is null for $socialMediaType")
            )
        }
    }

    override suspend fun getCommentForPost(
        socialMediaType: SocialMediaType,
        contentId: String
    ): Resource<Comment> {
        return try {
            val response = when (socialMediaType) {
                SocialMediaType.Instagram -> apiService.getInstagramCommentForPost(contentId)
                SocialMediaType.YouTube -> apiService.getYoutubeCommentForVideo(contentId)
            }
            if (response.data.isNotEmpty()) {
                val data = response.data.first()
                Resource.Success(
                    Comment(
                        profilePicUrl = data.commenterProfileUrl,
                        userName = data.commenterDisplayName,
                        commentDate = getFormattedTimeSpan(LocalDateTime.parse(data.createdAt)),
                        text = data.text
                    )
                )
            } else Resource.Error()
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Resource.Error(throwable = e)
        }
    }

    private fun getFormattedTimeSpan(dateTime: LocalDateTime): String {
        val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return DateUtils.getRelativeTimeSpanString(millis).toString()
    }

}