package com.crater.android.feature.cash.data.di

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.cash.data.repository.AccountRepositoryImpl
import com.crater.android.feature.cash.data.repository.TransactionRepositoryImpl
import com.crater.android.feature.cash.domain.repository.AccountRepository
import com.crater.android.feature.cash.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideAccountRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ): AccountRepository {
        return AccountRepositoryImpl(apiService,cacheManager)
    }

    @Provides
    fun provideTransactionRepository(
        apiService: ApiService
    ): TransactionRepository {
        return TransactionRepositoryImpl(apiService)
    }
}