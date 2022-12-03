package com.crater.android.feature.invoicing.data.di

import com.crater.android.data.db.AppDatabase
import com.crater.android.feature.invoicing.data.dao.CustomerDao
import com.crater.android.feature.invoicing.data.dao.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao {
        return db.serviceDao
    }

    @Singleton
    @Provides
    fun provideCustomerDao(
        db: AppDatabase
    ): CustomerDao {
        return db.customerDao
    }

}