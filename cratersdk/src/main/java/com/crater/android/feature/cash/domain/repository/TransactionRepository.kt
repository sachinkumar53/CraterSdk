package com.crater.android.feature.cash.domain.repository

import com.crater.android.core.common.model.Name

interface TransactionRepository {

    suspend fun verifyUpiId(upiId: String): Name

    suspend fun requestPayment(
        upiId: String,
        amount: Double
    ): Boolean

    suspend fun generatePaymentLink(amount: Double): String

    suspend fun sendMoneyToSelf(amount: Double): Boolean

}