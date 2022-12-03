package com.crater.android.feature.expense.ui.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.core.data.Resource
import com.crater.android.feature.expense.domain.BankAccount
import com.crater.android.feature.expense.domain.model.ExpenseCategory
import com.crater.android.feature.expense.domain.model.TransactionFilter
import com.crater.android.feature.expense.domain.model.isEmpty
import com.crater.android.feature.expense.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    val repository: ExpenseRepository
) : ViewModel() {

    val allAccounts = repository.getAccounts().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    val allCategories = ExpenseCategory.values()

    var fromDate by mutableStateOf<LocalDate?>(null)
        private set

    var toDate by mutableStateOf<LocalDate?>(null)
        private set

    var selectedCategories = mutableStateListOf<ExpenseCategory>()
        private set

    var selectedAccounts = mutableStateListOf<String>()
        private set

    val isAllAccountSelected = derivedStateOf {
        selectedAccounts.size == allAccounts.value.size
    }

    val isAllCategoriesSelected = derivedStateOf {
        selectedCategories.size == allCategories.size
    }

    var fromDatePicker = false

    private val showFilteredResults = MutableStateFlow(TransactionFilter())
    val isResultFiltered = showFilteredResults.map { !it.isEmpty() }

    val transactions = showFilteredResults.flatMapLatest { filter ->
        repository.getFilteredTransactions(filter)
    }.flowOn(Dispatchers.IO).onStart {
        emit(Resource.Loading())
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        Resource.Error()
    )

    val isLoading = combine(
        transactions,
        repository.isDataLoading
    ) { resource, loading ->
        (resource is Resource.Loading) or loading
    }

    fun onCategoryChanged(category: ExpenseCategory, isChecked: Boolean) {
        if (isChecked) {
            if (!selectedCategories.contains(category))
                selectedCategories.add(category)
        } else {
            if (selectedCategories.contains(category))
                selectedCategories.remove(category)
        }
    }

    fun onAccountCheckChanged(account: BankAccount, isChecked: Boolean) {
        val accountNumber = account.accountNumber
        if (isChecked) {
            if (!selectedAccounts.contains(accountNumber))
                selectedAccounts.add(accountNumber)
        } else {
            if (selectedAccounts.contains(accountNumber))
                selectedAccounts.remove(accountNumber)
        }
    }

    fun onDateChanged(date: LocalDate) {
        if (fromDatePicker) {
            fromDate = date
        } else {
            toDate = date
        }
    }

    fun clearFilter() {
        fromDate = null
        toDate = null
        selectedCategories.clear()
        selectedAccounts.clear()
        showFilteredResults.value = TransactionFilter()
    }

    fun showFilteredResult() {
        showFilteredResults.value = TransactionFilter(
            fromDate = fromDate,
            toDate = toDate,
            categories = selectedCategories,
            accounts = selectedAccounts,
            isAllAccount = isAllAccountSelected.value or selectedAccounts.isEmpty(),
            isAllCategories = isAllCategoriesSelected.value or selectedCategories.isEmpty()
        )
    }

    fun fetchExpenseFromDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchExpensesFromDevice()
        }
    }
}