package com.crater.android.data.di

import android.content.Context
import com.crater.android.BuildConfig
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.db.AppDatabase
import com.crater.android.data.network.ApiService
import com.crater.android.data.network.AuthInterceptor
import com.crater.android.utils.CraterParsers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient(cacheManager: CacheManager): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)

            addInterceptor(AuthInterceptor(cacheManager))

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }.also {
                    addInterceptor(it)
                }
            }

        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        // FIXME: Provide Base URL using injection
        return Retrofit.Builder().baseUrl("https://craterprd-api.thecrater.co/")
            .addConverterFactory(GsonConverterFactory.create(CraterParsers.gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase(context)

}
