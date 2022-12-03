package com.crater.android.feature.dashboard.domain.repository

interface DashboardRepository {
    suspend fun fetchUserDetails()
}