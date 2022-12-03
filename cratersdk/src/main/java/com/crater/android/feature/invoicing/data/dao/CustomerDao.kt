package com.crater.android.feature.invoicing.data.dao

import androidx.room.*
import com.crater.android.feature.invoicing.data.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customers LIMIT 4")
    fun getRecentCustomers(): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers")
    fun getAllCustomers(): Flow<List<CustomerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomer(customer: CustomerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomers(customers: List<CustomerEntity>)

    @Transaction
    suspend fun updateCustomers(customers: List<CustomerEntity>) {
        clearCustomers()
        insertCustomers(customers)
    }

    @Query("DELETE FROM customers WHERE phone =:phone")
    suspend fun deleteCustomer(phone:String)

    @Query("DELETE FROM customers")
    suspend fun clearCustomers()
}