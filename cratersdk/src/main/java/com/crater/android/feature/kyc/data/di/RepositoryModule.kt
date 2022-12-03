package com.crater.android.feature.kyc.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.kyc.data.repository.VerificationRepositoryImpl
import com.crater.android.feature.kyc.domain.repository.VerificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun providePanVerificationRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ): VerificationRepository {
        return VerificationRepositoryImpl(apiService, cacheManager)
    }
}