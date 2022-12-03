package com.crater.android.feature.dashboard.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.dashboard.data.repository.DashboardRepositoryImpl
import com.crater.android.feature.dashboard.domain.repository.DashboardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideDashboardRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ): DashboardRepository {
        return DashboardRepositoryImpl(apiService, cacheManager)

    }
}