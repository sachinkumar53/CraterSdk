package com.crater.android.feature.social.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.social.data.repository.SocialMediaRepositoryImpl
import com.crater.android.feature.social.domain.repository.SocialMediaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideSocialMediaRepository(
        cacheManager: CacheManager,
        apiService: ApiService
    ): SocialMediaRepository {
        return SocialMediaRepositoryImpl(cacheManager, apiService)
    }
}