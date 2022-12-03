package com.crater.android.data.network

import android.util.Base64
import com.crater.android.core.data.cache.CacheManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val cacheManager: CacheManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { cacheManager.getAccessToken().firstOrNull() }
        val newUrl = chain.request().url.newBuilder().build()
        val newRequest = chain.request().newBuilder().url(newUrl).apply {
            if (token == null) {
                val httpToken = String(Base64.decode(getTokenFromJNI(), Base64.DEFAULT))
                addHeader("Authorization", "Bearer $httpToken")
            } else {
                addHeader("Authorization", "Bearer $token")
            }

        }.build()
        return chain.proceed(newRequest)
    }

    companion object {
        @JvmStatic
        external fun getTokenFromJNI(): String

        init {
            System.loadLibrary("cratersdk")
        }
    }
}