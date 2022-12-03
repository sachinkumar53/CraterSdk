package com.crater.android.feature.cash.data.repository

import com.crater.android.core.common.model.Name
import com.crater.android.data.network.ApiService
import com.crater.android.feature.cash.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val apiService: ApiService
) : TransactionRepository {

    override suspend fun verifyUpiId(upiId: String): Name {
        val response = apiService.verifyUpiId(upiId)
        return Name(response.name)
    }

    override suspend fun requestPayment(upiId: String, amount: Double): Boolean {
        val response = apiService.initiateCollectRequest(upiId = upiId, amount = amount)
        return response.status.equals("SUCCESS", true)
    }

    override suspend fun generatePaymentLink(amount: Double): String {
        val response = apiService.generateUpiLink(amount)
        return response.data.upiUri
    }

    override suspend fun sendMoneyToSelf(amount: Double): Boolean {
        apiService.sendMoneyToSelf(amount)
        return true
    }
}