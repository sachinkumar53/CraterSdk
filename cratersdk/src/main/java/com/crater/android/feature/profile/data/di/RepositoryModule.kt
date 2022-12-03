package com.crater.android.feature.profile.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.profile.data.repository.ProfileRepositoryImpl
import com.crater.android.feature.profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideProfileRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ):ProfileRepository{
        return ProfileRepositoryImpl(apiService,cacheManager)
    }
}