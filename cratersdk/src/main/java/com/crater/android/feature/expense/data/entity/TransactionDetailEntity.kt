package com.crater.android.feature.expense.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.crater.android.feature.expense.data.sms.merchant.Bank
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.domain.model.TransactionType
import java.time.LocalDate

@Entity(
    tableName = "transaction_details",
    indices = [
        Index(
            value = ["refNumber"],
            unique = true
        )
    ]
)
data class TransactionDetailEntity(
    val merchantName: String?,
    val amount: Double?,
    val date: LocalDate,
    val category: ExpenseCategory,
    val transactionType: TransactionType,
    val refNumber: String?,
    val accountNumber: String?,
    val cardNumber: String?,
    val bank: Bank
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
