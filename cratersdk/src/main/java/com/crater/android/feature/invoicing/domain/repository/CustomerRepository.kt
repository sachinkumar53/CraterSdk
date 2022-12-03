package com.crater.android.feature.invoicing.domain.repository

import com.crater.android.core.data.Resource
import com.crater.android.feature.invoicing.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    fun getRecentCustomers(): Flow<Resource<List<Customer>>>
    fun getAllCustomers(): Flow<Resource<List<Customer>>>

    suspend fun fetchCustomers(): Boolean

    suspend fun addCustomer(customer: Customer): String
    suspend fun updateCustomer(customer: Customer)
    suspend fun deleteCustomer(phone: String): String
}