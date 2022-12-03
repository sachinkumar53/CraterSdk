package com.crater.android.feature.profile.domain.repository

import com.crater.android.data.model.profile.UserDetails
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfileDetails(): Flow<UserDetails>

    suspend fun logout(): String
}