package com.crater.android.feature.invoicing.data.repository

import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.data.dao.ServiceDao
import com.crater.android.feature.invoicing.data.entity.ServiceEntity
import com.crater.android.feature.invoicing.data.mapper.toEntity
import com.crater.android.feature.invoicing.data.mapper.toModel
import com.crater.android.feature.invoicing.domain.model.Service
import com.crater.android.feature.invoicing.domain.repository.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ServiceRepositoryImpl(
    private val serviceDao: ServiceDao
) : ServiceRepository {

    /*fun getAllService(): Flow<List<ServiceModel>> = serviceDao.getAllService()
        .map { list ->
            list.map {
                it.toModel()
            }
        }.flowOn(Dispatchers.IO)
*/

    override fun getRecentServices(): Flow<Resource<List<Service>>> {
        return serviceDao.getRecentServices()
            .map<List<ServiceEntity>, Resource<List<Service>>> { list ->
                Resource.Success(list.map { it.toModel() })
            }.onStart {
                emit(Resource.Loading())
            }.catch {
                emit(Resource.Error(it))
            }.flowOn(Dispatchers.IO)
    }

    override fun getAllServices(): Flow<Resource<List<Service>>> {
        return serviceDao.getAllService()
            .map<List<ServiceEntity>, Resource<List<Service>>> { list ->
                Resource.Success(list.map { it.toModel() })
            }.onStart {
                emit(Resource.Loading())
            }.catch {
                emit(Resource.Error(it))
            }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertService(service: Service) {
        serviceDao.insertService(service.toEntity())
    }
}

