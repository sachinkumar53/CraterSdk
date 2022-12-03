package com.crater.android.feature.expense.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.domain.model.TransactionType
import com.crater.android.feature.expense.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    var amount by mutableStateOf("")
        private set

    var paidTo by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf<ExpenseCategory?>(null)
        private set

    private val _bankAccount = MutableStateFlow<BankAccount?>(null)
    val bankAccount = _bankAccount.asStateFlow()


    var selectedDate: LocalDate by mutableStateOf(LocalDate.now())
        private set

    var isAmountError by mutableStateOf(false)
        private set

    var isPaidToError by mutableStateOf(false)
        private set

    var isAccountError by mutableStateOf(false)
        private set

    var isCategoryError by mutableStateOf(false)
        private set

    val accountsFlow = repository.getAccounts()

    fun onAddClick(
        onComplete: () -> Unit
    ) {
        if (validateFields()) {
            viewModelScope.launch {
                repository.insert(
                    TransactionDetailEntity(
                        merchantName = paidTo,
                        amount = amount.toDoubleOrNull(),
                        date = selectedDate,
                        category = selectedCategory!!,
                        transactionType = TransactionType.Debit,
                        refNumber = null,
                        accountNumber = bankAccount.value?.accountNumber,
                        cardNumber = null,
                        bank = bankAccount.value?.bank!!
                    )
                )
                onComplete()
            }
        }
    }

    fun onAccountNumberChanged(account: BankAccount) {
        _bankAccount.value = account
        isAccountError = false
    }

    fun onAmountChanged(amt: String) {
        amount = amt
        isAmountError = false
    }

    fun onPaidToChanged(to: String) {
        paidTo = to
        isPaidToError = false
    }

    fun onCategoryChanged(category: String) {
        try {
            selectedCategory = ExpenseCategory.valueOf(category)
            isCategoryError = false
        } catch (e: Exception) {
        }
    }

    fun onDateChanged(date: LocalDate) {
        selectedDate = date
    }

    private fun validateFields(): Boolean {
        if (amount.isBlank()) {
            isAmountError = true
            return false
        }

        if (paidTo.isBlank()) {
            isPaidToError = true
            return false
        }

        if (bankAccount.value == null) {
            isAccountError = true
            return false
        }

        if (selectedCategory == null) {
            isCategoryError = true
            return false
        }
        return true
    }
}
