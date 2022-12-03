package com.crater.android.feature.cash.data.repository

import com.crater.android.core.data.cache.CacheManager
import com.crater.android.core.data.mapper.toUserDetails
import com.crater.android.core.util.DateFormatterUtil.formatInMediumStyle
import com.crater.android.data.network.ApiService
import com.crater.android.feature.cash.domain.model.AccountDetail
import com.crater.android.feature.cash.domain.model.Amount
import com.crater.android.feature.cash.domain.model.Transaction
import com.crater.android.feature.cash.domain.repository.AccountRepository
import com.crater.android.feature.expense.domain.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime


class AccountRepositoryImpl(
    private val apiService: ApiService,
    private val cacheManager: CacheManager
) : AccountRepository {

    override suspend fun getAccountDetail(): AccountDetail {
        val accountDetailResponse = apiService.fetchAccountDetails()
        val balanceResponse = apiService.getBalance()

        return AccountDetail(
            availableBalance = Amount(balanceResponse.presentBalance),
            accountNo = accountDetailResponse.accountNo,
            ifsc = accountDetailResponse.ifsc,
            upiId = accountDetailResponse.upiId,
            upiQr = accountDetailResponse.upiQr
        )
    }

    override suspend fun getStatement(): List<Transaction> = withContext(Dispatchers.Default) {
        val response = apiService.getStatement()
        val transactions = response.statement.map { statement ->

            val transactionType = if (statement.type.equals("CREDIT", true)) {
                TransactionType.Credit
            } else {
                TransactionType.Debit
            }
            val transactionId = if (statement.transferType.equals("upi", true)) {
                statement.payerVpa ?: statement.accountNumber
            } else {
                statement.accountNumber
            }

            Transaction(
                title = transactionId,
                date = formatInMediumStyle(LocalDateTime.parse(statement.timestamp).toLocalDate()),
                depositAmount = Amount(
                    if (transactionType == TransactionType.Credit)
                        statement.depositAmount
                    else statement.withdrawalAmount
                ),
                transactionType = transactionType,
                availableAmount = Amount(statement.balance)
            )
        }
        return@withContext transactions
    }

    override suspend fun getUserBankAccountNumber(): String {
        return apiService.getUserBankAccountDetails().accountNo
    }


    override suspend fun createVirtualAccountWithCustomUpiId(upiId: String): Boolean {
        val response = apiService.createVirtualAccountWithCustomUpi(upiId).code == 200
        if (response) {
            val details = apiService.getOnBoardingDetails()
            cacheManager.setUserDetails(details.toUserDetails())
        }
        return response
    }

    override suspend fun validateUpi(upiId: String): Result<Boolean> {
        return runCatching {
            apiService.validateUpiId(upiId)
        }.mapCatching { it.equals("UPI ID Available", true) }
    }
}