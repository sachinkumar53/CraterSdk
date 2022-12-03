package com.crater.android.feature.kyc.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.mapper.toUserDetails
import com.crater.android.data.network.ApiService
import com.crater.android.feature.kyc.domain.model.BankDetails
import com.crater.android.feature.kyc.domain.model.PanNumber
import com.crater.android.feature.kyc.domain.repository.VerificationRepository

class VerificationRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : VerificationRepository {

    override suspend fun verifyBankDetails(details: BankDetails): Boolean {
        val response = apiService.verifyBankDetails(
            name = details.accountName.name,
            accountNumber = details.accountNumber.accNo,
            ifsc = details.ifsc.code,
            email = details.email.id
        ).code == 200
        if (response) {
            val detailsResponse = apiService.getOnBoardingDetails()
            cacheManager.setUserDetails(detailsResponse.toUserDetails())
        }
        return response
    }

    override suspend fun verifyPanNumber(panNumber: PanNumber): Boolean {
        val response = apiService.verifyPanNumber(panNumber.value)
            .message.kycStatus.equals("SUCCESS", true)
        if (response) {
            val detailsResponse = apiService.getOnBoardingDetails()
            cacheManager.setUserDetails(detailsResponse.toUserDetails())
        }
        return response
    }
}