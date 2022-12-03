package com.crater.android.core.data.cache

import com.crater.android.data.model.profile.UserDetails
import kotlinx.coroutines.flow.Flow

interface CacheManager {

    suspend fun setUserDetails(userDetails: UserDetails)

    fun getAccessToken(): Flow<String?>
    suspend fun setAccessToken(token: String)

    fun getUserDetails(): Flow<UserDetails>

    fun getPhylloUserId(): Flow<String?>

    fun getPhylloSdkToken(): Flow<String?>

    fun getInstagramAccountId(): Flow<String?>

    fun getYoutubeAccountId(): Flow<String?>

    suspend fun clearCache()

    suspend fun setPhylloUserId(id: String)

    suspend fun setPhylloSdkToken(token: String)

    suspend fun setYoutubeAccountId(id: String)

    suspend fun setInstagramAccountId(id: String)

    suspend fun removeInstagramAccountId()

    suspend fun removeYoutubeAccountId()

}