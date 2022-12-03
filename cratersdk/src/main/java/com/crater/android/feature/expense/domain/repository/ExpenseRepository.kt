package com.crater.android.feature.expense.domain.repository

import com.crater.android.core.data.Resource
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.CategoryWiseExpense
import com.crater.android.feature.expense.domain.model.MonthWiseExpense
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.feature.expense.domain.model.TransactionFilter
import kotlinx.coroutines.flow.Flow
import java.time.YearMonth

interface ExpenseRepository {

    val isDataLoading: Flow<Boolean>

    fun getExpensesGroupedByMonth(): Flow<Resource<Map<YearMonth, List<TransactionDetail>>>>
    fun getAccounts(): Flow<List<BankAccount>>
    suspend fun getAccountsSync(): List<BankAccount>
    suspend fun insert(transactionDetail: TransactionDetailEntity)
    suspend fun fetchExpensesFromDevice(): List<TransactionDetailEntity>

    fun getFilteredTransactions(
        filter: TransactionFilter
    ): Flow<Resource<Map<YearMonth, List<TransactionDetail>>>>

    fun getMonthWiseExpense(): Flow<List<MonthWiseExpense>>
    fun getCategoryWiseExpenseForMonth(yearMonth: YearMonth): Flow<List<CategoryWiseExpense>>
    fun getRecentTransactions(): Flow<List<TransactionDetail>>
}