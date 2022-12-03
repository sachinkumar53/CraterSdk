package com.crater.android.feature.social.domain.repository

import com.crater.android.core.data.Resource
import com.crater.android.data.dto.engagement.EngagementResponse
import com.crater.android.data.dto.phyllo.CreateUserResponse
import com.crater.android.data.dto.phyllo.GetProfileResponse
import com.crater.android.data.model.social.SocialMediaType
import com.crater.android.feature.social.data.dto.disconnect.AccountDisconnectResponse
import com.crater.android.feature.social.domain.model.Comment
import com.crater.android.feature.social.domain.model.PhylloSdkToken
import com.crater.android.feature.social.domain.model.PhylloUserId
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate


interface SocialMediaRepository {
    fun getPhylloSdkToken(): PhylloSdkToken?
    fun getPhylloUserId(): PhylloUserId?

    fun getInstagramAccountIdSync(): String?
    fun getYoutubeAccountIdSync(): String?

    fun getInstagramAccountId(): Flow<String?>
    fun getYoutubeAccountId(): Flow<String?>

    suspend fun createUser(): Resource<CreateUserResponse>

    suspend fun generateSdkToken(): Resource<PhylloSdkToken>

    suspend fun getYoutubeProfile(): Resource<GetProfileResponse>

    suspend fun getInstagramProfile(): Resource<GetProfileResponse>

    suspend fun getYoutubeEngagement(
        fromDate: LocalDate = LocalDate.now().minusDays(29), //LocalDate.parse("2000-01-01"),
        toDate: LocalDate = LocalDate.now().minusDays(1)
    ): Resource<EngagementResponse>

    suspend fun getInstagramEngagement(
        fromDate: LocalDate = LocalDate.now().minusDays(29),
        toDate: LocalDate = LocalDate.now().minusDays(1)
    ): Resource<EngagementResponse>

    suspend fun disconnectAccount(socialMediaType: SocialMediaType): Resource<AccountDisconnectResponse>

    suspend fun getCommentForPost(
        socialMediaType: SocialMediaType,
        contentId: String
    ): Resource<Comment>
}