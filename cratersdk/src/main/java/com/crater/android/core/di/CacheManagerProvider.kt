package com.crater.android.core.di

import android.content.Context
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.cache.SecureCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CacheManagerProvider {

    @Singleton
    @Provides
    fun provideCacheManager(
        @ApplicationContext
        context: Context
    ): CacheManager {
        return SecureCacheManager(context)
    }
}