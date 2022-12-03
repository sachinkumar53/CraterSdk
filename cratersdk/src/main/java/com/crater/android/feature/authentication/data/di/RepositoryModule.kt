package com.crater.android.feature.authentication.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.authentication.data.repository.AuthenticationRepositoryImpl
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthenticationRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(apiService, cacheManager)
    }
}