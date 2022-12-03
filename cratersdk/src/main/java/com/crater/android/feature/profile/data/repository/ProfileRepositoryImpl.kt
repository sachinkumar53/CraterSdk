package com.crater.android.feature.profile.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.data.network.ApiService
import com.crater.android.feature.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow


class ProfileRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : ProfileRepository {

    override fun getProfileDetails(): Flow<UserDetails> {
        return cacheManager.getUserDetails()
    }

    override suspend fun logout(): String {
        return apiService.logout().message
    }
}