package com.crater.android.feature.cash.domain.repository

import com.crater.android.feature.cash.domain.model.AccountDetail
import com.crater.android.feature.cash.domain.model.Transaction


interface AccountRepository {

    suspend fun getAccountDetail(): AccountDetail

    suspend fun getStatement(): List<Transaction>

    suspend fun getUserBankAccountNumber(): String

    suspend fun createVirtualAccountWithCustomUpiId(upiId: String): Boolean

    suspend fun validateUpi(upiId: String): Result<Boolean>
}