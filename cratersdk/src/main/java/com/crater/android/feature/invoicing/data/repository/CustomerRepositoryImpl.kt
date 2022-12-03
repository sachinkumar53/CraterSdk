package com.crater.android.feature.invoicing.data.repository

import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.data.dao.CustomerDao
import com.crater.android.feature.invoicing.data.entity.CustomerEntity
import com.crater.android.data.network.ApiService
import com.crater.android.feature.invoicing.data.mapper.toDomain
import com.crater.android.feature.invoicing.data.mapper.toDto
import com.crater.android.feature.invoicing.data.mapper.toEntity
import com.crater.android.feature.invoicing.domain.model.Customer
import com.crater.android.feature.invoicing.domain.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class CustomerRepositoryImpl(
    private val apiService: ApiService,
    private val dao: CustomerDao
) : CustomerRepository {

    override fun getRecentCustomers(): Flow<Resource<List<Customer>>> {
        return dao.getRecentCustomers()
            .map<List<CustomerEntity>, Resource<List<Customer>>> { list ->
                Resource.Success(data = list.map { it.toDomain() })
            }.onStart {
                emit(Resource.Loading())
            }.catch {
                emit(Resource.Error(it))
            }.flowOn(Dispatchers.IO)
    }

    override fun getAllCustomers(): Flow<Resource<List<Customer>>> {
        return dao.getAllCustomers().map<List<CustomerEntity>, Resource<List<Customer>>> { list ->
            Resource.Success(data = list.map { it.toDomain() })
        }.onStart {
            emit(Resource.Loading())
        }.catch {
            emit(Resource.Error(it))
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun fetchCustomers(): Boolean = withContext(Dispatchers.Default) {
        val customers = apiService.getCustomers().map { it.toEntity() }
        dao.updateCustomers(customers)
        //dao.getAllCustomers().firstOrNull()?.map { it.toDomain() } ?: emptyList()
        true
    }

    override suspend fun addCustomer(customer: Customer): String {
        val response = apiService.addCustomer(
            name = customer.name,
            phone = customer.phone,
            emailId = customer.emailId,
            pan = customer.pan,
            gst = customer.gst,
            address = customer.address,
            city = customer.city,
            state = customer.state,
            pinCode = customer.pinCode,
            country = customer.country
        )
        dao.insertCustomer(customer.toEntity())
        return response
    }

    override suspend fun updateCustomer(customer: Customer) {
        apiService.updateCustomer(customer.phone, customer.toDto())
        dao.insertCustomer(customer.toEntity())
    }

    override suspend fun deleteCustomer(phone: String): String {
        val response = apiService.deleteCustomer(phone)
        dao.deleteCustomer(phone)
        return response
    }

}