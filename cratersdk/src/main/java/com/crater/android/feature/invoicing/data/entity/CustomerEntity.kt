package com.crater.android.feature.invoicing.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "customers",
    indices = [Index(value = ["name", "phone", "email"], unique = true)]
)
data class CustomerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String,
    val email: String,
    val panDetails: String?,
    val gst: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val pinCode: String?,
    val country: String?,
)