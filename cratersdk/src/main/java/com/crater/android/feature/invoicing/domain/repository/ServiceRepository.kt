package com.crater.android.feature.invoicing.domain.repository

import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.domain.model.Service
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {

    fun getRecentServices(): Flow<Resource<List<Service>>>

    fun getAllServices(): Flow<Resource<List<Service>>>

    suspend fun insertService(service: Service)
}