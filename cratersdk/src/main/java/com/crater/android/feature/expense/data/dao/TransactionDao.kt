package com.crater.android.feature.expense.data.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.CategoryWiseExpense
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.domain.model.MonthWiseExpense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.YearMonth

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_details ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionDetailEntity>>

    @Query("SELECT * FROM transaction_details ORDER BY date DESC")
    suspend fun getAllTransactionsSync(): List<TransactionDetailEntity>

    @Query("SELECT * FROM transaction_details ORDER BY date DESC LIMIT 4")
    fun getRecentTransactions(): Flow<List<TransactionDetailEntity>>

    @Query("SELECT DISTINCT accountNumber,bank FROM transaction_details WHERE accountNumber IS NOT NULL")
    fun getAllAccounts(): Flow<List<BankAccount>>

    @Query("SELECT DISTINCT accountNumber,bank FROM transaction_details WHERE accountNumber IS NOT NULL")
    suspend fun getAllAccountsSync(): List<BankAccount>

    @Query("SELECT DISTINCT cardNumber as accountNumber,bank FROM transaction_details WHERE cardNumber IS NOT NULL")
    fun getAllCards(): Flow<List<BankAccount>>

    @Query("SELECT DISTINCT cardNumber as accountNumber,bank FROM transaction_details WHERE cardNumber IS NOT NULL")
    suspend fun getAllCardsSync(): List<BankAccount>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(transactions: List<TransactionDetailEntity>)

    @Query("DELETE FROM transaction_details")
    suspend fun clear()

    @RawQuery(observedEntities = [TransactionDetailEntity::class])
    fun getTransactionsViaQuery(query: SupportSQLiteQuery): Flow<List<TransactionDetailEntity>>

    /*@Query(
        """
            SELECT t1.yearMonth, CASE WHEN t1.expense >= 0 THEN t1.expense ELSE 0 END as expense
            FROM (SELECT SUM(CASE transactionType WHEN 'Debit' THEN amount WHEN 'Credit' THEN -amount ELSE 0 END) as expense,
            strftime('%Y-%m',date) as yearMonth FROM transaction_details
            WHERE date BETWEEN date('now','start of month','-6 months') AND date('now')
            GROUP BY yearMonth ORDER BY yearMonth ASC) as t1
        """
    )*/
    @Query(
        """
            SELECT SUM(amount) as expense,
            strftime('%Y-%m', date) as yearMonth FROM transaction_details
            WHERE transactionType = 'Debit'
            AND date BETWEEN date('now','start of month','-5 months') AND date('now')
            GROUP BY yearMonth ORDER BY yearMonth ASC
        """
    )
    fun getMonthWiseExpense(): Flow<List<MonthWiseExpense>>

    //CASE transactionType WHEN 'Debit' THEN amount WHEN 'Credit' THEN -amount ELSE 0 END
    @Query(
        """
        SELECT SUM(amount) as expense,category FROM transaction_details 
        WHERE strftime('%Y-%m',date) = :yearMonth
        AND transactionType = 'Debit'
        GROUP BY category
        """
    )
    fun getCategoryWiseExpenseForMonth(yearMonth: YearMonth): Flow<List<CategoryWiseExpense>>

    fun getFilteredTransactions(
        accountList: List<String>,
        categoryList: List<ExpenseCategory>,
        fromDate: LocalDate?,
        toDate: LocalDate?,
        isAllAccount: Boolean,
        isAllCategory: Boolean
    ): Flow<List<TransactionDetailEntity>> {
        val queryBuilder = StringBuilder("SELECT * FROM transaction_details ")

        when {
            //If all accounts and all categories are selected, no need to filter anything
            isAllAccount && isAllCategory -> {
                if (fromDate != null || toDate != null)
                    queryBuilder.append("WHERE ") //For date filter
            }

            //If all accounts are selected, apply filter only on categories
            isAllAccount -> {
                val categories = categoryList.joinToString { "'$it'" }
                queryBuilder.append("WHERE ")
                queryBuilder.append("category IN ($categories) ")
                if (fromDate != null || toDate != null)
                    queryBuilder.append("AND ") //For date filter
            }

            //If all categories are selected, apply filter only on accounts
            isAllCategory -> {
                val accounts = accountList.joinToString() { "'$it'" }
                queryBuilder.append("WHERE ")
                queryBuilder.append("(accountNumber IN ($accounts) ")
                queryBuilder.append("OR cardNumber IN ($accounts)) ")
                if (fromDate != null || toDate != null)
                    queryBuilder.append("AND ") //For date filter
            }

            //If neither all accounts nor all categories are selected, apply filter on both of them
            else -> {
                val accounts = accountList.joinToString() { "'$it'" }
                val categories = categoryList.joinToString { "'$it'" }
                queryBuilder.append("WHERE ")
                queryBuilder.append("(accountNumber IN ($accounts) ")
                queryBuilder.append("OR cardNumber IN ($accounts)) ")
                queryBuilder.append("AND category IN ($categories) ")
                if (fromDate != null || toDate != null)
                    queryBuilder.append("AND ") //For date filter
            }
        }

        //Apply date filter according to their nullability
        val params: Array<Any> = if (fromDate != null && toDate != null) {
            queryBuilder.append("date BETWEEN ? AND ? ")
            arrayOf(fromDate.toString(), toDate.toString())
        } else if (fromDate != null) {
            queryBuilder.append("date >= ? ")
            arrayOf(fromDate.toString())
        } else if (toDate != null) {
            queryBuilder.append("date <= ? ")
            arrayOf(toDate.toString())
        } else {
            emptyArray()
        }

        //Always order by date in descending order
        queryBuilder.append("ORDER BY date DESC")

        println(queryBuilder)
        return getTransactionsViaQuery(SimpleSQLiteQuery(queryBuilder.toString(), params))
    }
}
