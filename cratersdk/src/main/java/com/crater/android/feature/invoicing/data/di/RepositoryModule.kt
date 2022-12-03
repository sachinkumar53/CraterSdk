package com.crater.android.feature.invoicing.data.di

import android.content.Context
import com.crater.android.core.data.cache.CacheManager
import com.crater.android.data.network.ApiService
import com.crater.android.feature.invoicing.data.dao.CustomerDao
import com.crater.android.feature.invoicing.data.dao.ServiceDao
import com.crater.android.feature.invoicing.data.repository.ContactsRepositoryImpl
import com.crater.android.feature.invoicing.data.repository.CustomerRepositoryImpl
import com.crater.android.feature.invoicing.data.repository.InvoiceRepositoryImpl
import com.crater.android.feature.invoicing.data.repository.ServiceRepositoryImpl
import com.crater.android.feature.invoicing.domain.repository.ContactsRepository
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import com.crater.android.feature.invoicing.domain.repository.InvoiceRepository
import com.crater.android.feature.invoicing.domain.repository.ServiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideServiceRepository(
        serviceDao: ServiceDao
    ): ServiceRepository {
        return ServiceRepositoryImpl(serviceDao)
    }

    @Provides
    fun provideContactsRepository(
        @ApplicationContext
        context: Context
    ): ContactsRepository {
        return ContactsRepositoryImpl(context)
    }

    @Provides
    fun provideCustomerRepository(
        apiService: ApiService,
        dao: CustomerDao
    ): CustomerRepository {
        return CustomerRepositoryImpl(apiService, dao)
    }

    @Provides
    fun provideInvoiceRepository(
        apiService: ApiService,
        cacheManager: CacheManager
    ): InvoiceRepository {
        return InvoiceRepositoryImpl(apiService, cacheManager)
    }
}