package com.crater.android.feature.expense.data.repository

import com.crater.android.core.data.Resource
import com.crater.android.core.util.Mapper
import com.crater.android.feature.expense.data.dao.TransactionDao
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.data.mapper.TransactionMapper
import com.crater.android.feature.expense.data.sms.SmsFetcher
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.CategoryWiseExpense
import com.crater.android.feature.expense.domain.model.MonthWiseExpense
import com.crater.android.feature.expense.domain.model.TransactionDetail
import com.crater.android.feature.expense.domain.model.TransactionFilter
import com.crater.android.feature.expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.time.YearMonth

class ExpenseRepositoryImpl(
    private val smsFetcher: SmsFetcher,
    private val transactionDao: TransactionDao,
    private val transactionMapper: TransactionMapper
) : ExpenseRepository, Mapper<TransactionDetailEntity, TransactionDetail> by transactionMapper {

    //private val permissionFlow = Permission(Manifest.permission.READ_SMS).statusFlow
    private val _isDataLoading = MutableStateFlow(false)
    override val isDataLoading: Flow<Boolean> = _isDataLoading

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getExpensesGroupedByMonth(
    ): Flow<Resource<Map<YearMonth, List<TransactionDetail>>>> {
        return transactionDao.getAllTransactions()
            .mapLatest<List<TransactionDetailEntity>, Resource<Map<YearMonth, List<TransactionDetail>>>> { data ->
                getGroupedTransactionList(data).let { Resource.Success(it) }
            }.catch {
                emit(Resource.Error(it))
            }.onStart {
                emit(Resource.Loading())
            }
    }

    override suspend fun fetchExpensesFromDevice(): List<TransactionDetailEntity> {
        return withContext(Dispatchers.IO) {
            _isDataLoading.update { true }
            //delay(5000L)
            try {
                transactionDao.clear()
                val data = smsFetcher.fetchSms()
                if (!data.isNullOrEmpty()) {
                    transactionDao.insertAll(data)
                    _isDataLoading.update { false }
                    return@withContext data
                } else throw IOException("")
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                e.printStackTrace()
                _isDataLoading.update { false }
                emptyList()
            }
        }
    }

    override fun getAccounts(): Flow<List<BankAccount>> {
        return combine(
            transactionDao.getAllAccounts(),
            transactionDao.getAllCards(),
        ) { accounts, cards ->
            val allAccounts = accounts + cards
            allAccounts.distinctBy { it.accountNumber }
        }
    }

    override suspend fun getAccountsSync(): List<BankAccount> {
        val accounts = transactionDao.getAllAccountsSync()
        val cards = transactionDao.getAllCardsSync()
        val allAccounts = accounts + cards
        return allAccounts.distinctBy { it.accountNumber }
    }

    override suspend fun insert(transactionDetail: TransactionDetailEntity) {
        transactionDao.insert(transactionDetail)
    }

    private suspend fun getGroupedTransactionList(
        data: List<TransactionDetailEntity>
    ): Map<YearMonth, List<TransactionDetail>> = withContext(Dispatchers.IO) {
        data.groupBy {
            YearMonth.of(it.date.year, it.date.month)
        }.mapValues { e ->
            e.value.map { it.map() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFilteredTransactions(
        filter: TransactionFilter
    ): Flow<Resource<Map<YearMonth, List<TransactionDetail>>>> {
        return transactionDao.getFilteredTransactions(
            accountList = filter.accounts,
            categoryList = filter.categories,
            fromDate = filter.fromDate,
            toDate = filter.toDate,
            isAllAccount = filter.isAllAccount,
            isAllCategory = filter.isAllCategories
        )
            .mapLatest<List<TransactionDetailEntity>, Resource<Map<YearMonth, List<TransactionDetail>>>> { data ->
                getGroupedTransactionList(data).let {
                    Resource.Success(it)
                }
            }
            .onStart {
                emit(Resource.Loading())
            }.flowOn(Dispatchers.IO)
            .distinctUntilChanged()
    }

    override fun getMonthWiseExpense(): Flow<List<MonthWiseExpense>> {
        return transactionDao.getMonthWiseExpense()
    }

    override fun getCategoryWiseExpenseForMonth(yearMonth: YearMonth): Flow<List<CategoryWiseExpense>> {
        return transactionDao.getCategoryWiseExpenseForMonth(yearMonth)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getRecentTransactions(): Flow<List<TransactionDetail>> {
        return transactionDao.getRecentTransactions().mapLatest { data ->
            data.map { it.map() }
        }
    }
}