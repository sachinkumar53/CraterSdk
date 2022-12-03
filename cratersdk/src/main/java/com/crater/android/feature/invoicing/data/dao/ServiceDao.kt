package com.crater.android.feature.invoicing.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crater.android.feature.invoicing.data.entity.ServiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {

    @Query("SELECT * FROM services ORDER BY id DESC LIMIT 4")
    fun getRecentServices(): Flow<List<ServiceEntity>>

    @Query("SELECT * FROM services ORDER BY id DESC")
    fun getAllService(): Flow<List<ServiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertService(service: ServiceEntity)

    @Query("DELETE FROM services")
    suspend fun clearServices()
}