package com.crater.android.feature.invoicing.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "services",
    indices = [Index(value = ["name"], unique = true)]
)
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val price: String,
    val notes: String? = null,
    val SAC: String?
)