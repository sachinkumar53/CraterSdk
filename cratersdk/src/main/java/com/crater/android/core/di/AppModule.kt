package com.crater.android.core.di

import com.crater.android.core.ui.event.EventHandler
import com.crater.android.core.ui.event.EventHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindEventHandler(eventHandlerImpl: EventHandlerImpl): EventHandler

}
