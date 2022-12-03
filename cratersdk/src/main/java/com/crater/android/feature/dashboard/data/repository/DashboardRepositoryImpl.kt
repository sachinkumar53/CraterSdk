package com.crater.android.feature.dashboard.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.mapper.toUserDetails
import com.crater.android.data.network.ApiService
import com.crater.android.feature.dashboard.domain.repository.DashboardRepository

class DashboardRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : DashboardRepository {

    override suspend fun fetchUserDetails() {
        val response = apiService.getOnBoardingDetails()
        val details = response.toUserDetails()
        cacheManager.setUserDetails(details)
    }


}