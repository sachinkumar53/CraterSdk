package com.crater.android.feature.authentication.domain.repository

import com.crater.android.data.model.profile.UserDetails
import com.crater.android.feature.authentication.domain.model.AuthFlowType

interface AuthenticationRepository {
    suspend fun getUserDetails(): UserDetails
    suspend fun getAccessToken(): String?

    suspend fun registerUser(phone: String, referralCode: String): Boolean

    suspend fun login(phone: String): Boolean

    suspend fun verifyOtp(
        flowType: AuthFlowType,
        phone: String,
        otp: String,
        inviteCode: String? = null
    ): Boolean

    suspend fun resendOtp(phone: String): Boolean

    suspend fun submitUserDetails(userDetails: UserDetails): Boolean
}