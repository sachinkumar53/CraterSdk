package com.crater.android.feature.authentication.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.mapper.toUserDetails
import com.crater.android.data.model.profile.UserDetails
import com.crater.android.data.network.ApiService
import com.crater.android.feature.authentication.domain.model.AuthFlowType
import com.crater.android.feature.authentication.domain.repository.AuthenticationRepository
import kotlinx.coroutines.flow.firstOrNull

class AuthenticationRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : AuthenticationRepository {

    override suspend fun getAccessToken(): String? {
        return cacheManager.getAccessToken().firstOrNull()
    }

    override suspend fun getUserDetails(): UserDetails {
        val response = apiService.getOnBoardingDetails()
        val details = response.toUserDetails()
        cacheManager.setUserDetails(details)
        return details
    }

    override suspend fun registerUser(phone: String, referralCode: String): Boolean {
        val response = apiService.registerUser(phone, referralCode)
        return response.code == 200
    }

    override suspend fun login(phone: String): Boolean {
        val response = apiService.login(phone)
        return response.code == 200
    }

    override suspend fun verifyOtp(
        flowType: AuthFlowType,
        phone: String,
        otp: String,
        inviteCode: String?
    ): Boolean {
        val response = when (flowType) {
            AuthFlowType.LOGIN -> apiService.verifyLoginOtp(phone, otp)
            AuthFlowType.REGISTRATION -> apiService.verifyRegisterOtp(phone, otp, inviteCode)
        }
        cacheManager.setAccessToken(response.token.accessToken)
        return response.code == 200
    }

    override suspend fun resendOtp(phone: String): Boolean {
        val response = apiService.resendOtp(phone)
        return response.code == 200
    }

    override suspend fun submitUserDetails(userDetails: UserDetails): Boolean {
        val response = apiService.addPersonalDetails(
            firstName = userDetails.userName.firstName,
            lastName = userDetails.userName.lastName,
            gender = userDetails.gender,
            dob = userDetails.dob,
            email = userDetails.emailId,
            city = userDetails.city,
            state = userDetails.state,
            pinCode = userDetails.pinCode
        )
        return response.code == 200
    }
}