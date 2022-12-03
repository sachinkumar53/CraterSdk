package com.crater.android.core.util

import android.content.Context
import android.util.Base64
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.module.LibraryGlideModule
import com.bumptech.glide.signature.ObjectKey
import com.crater.android.data.network.ApiService
import com.crater.android.feature.expense.data.sms.merchant.Bank
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import io.reactivex.disposables.CompositeDisposable
import java.nio.ByteBuffer

@GlideModule
class GlideBase64Decoder : LibraryGlideModule() {

    private fun getApiService(context: Context): ApiService {
        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(context, GlideEntryPoint::class.java)
        return hiltEntryPoint.apiService()
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.append(
            Bank::class.java,
            ByteBuffer::class.java,
            MyImageLoaderFactory(getApiService(context.applicationContext))
        )
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GlideEntryPoint {
        fun apiService(): ApiService
    }
}

class MyImageLoaderFactory(
    private val apiService: ApiService
) : ModelLoaderFactory<Bank, ByteBuffer> {

    override fun teardown() {

    }

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Bank, ByteBuffer> {
        return MyImageLoader(apiService)
    }
}


class MyImageLoader(private val apiService: ApiService) : ModelLoader<Bank, ByteBuffer> {

    override fun buildLoadData(
        model: Bank,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<ByteBuffer>? {
        val key = "code:${model.name};width:$width;height:$height"
        return ModelLoader.LoadData(
            ObjectKey(key),
            MyImageDataFetcher(apiService, model)
        )
    }

    override fun handles(model: Bank): Boolean {
        return true
    }
}

class MyImageDataFetcher(
    private val apiService: ApiService,
    private val request: Bank
) : DataFetcher<ByteBuffer> {

    private val disposables = CompositeDisposable()

    override fun cleanup() {
        disposables.clear()
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in ByteBuffer>) {
        disposables.add(apiService.getBankLogo(request.name)
            .subscribe(
                { base64 ->
                    if (!base64.isNullOrEmpty()) {
                        val bytes = Base64.decode(base64, Base64.DEFAULT)
                        callback.onDataReady(ByteBuffer.wrap(bytes))
                    }
                }, {
                    if (it is Exception) {
                        callback.onLoadFailed(it)
                    } else {
                        callback.onLoadFailed(Exception(it))
                    }
                })
        )
    }

    override fun cancel() {
        disposables.clear()
    }

    override fun getDataClass(): Class<ByteBuffer> {
        return ByteBuffer::class.java
    }

    override fun getDataSource(): DataSource {
        return DataSource.REMOTE
    }
}