package com.crater.android.feature.expense.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.feature.expense.domain.repository.ExpenseRepository
import com.madrapps.plot.line.DataPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class ExpenseSummaryViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {
    val colors = listOf(
        Color(0xFF15FFC7).toArgb(),
        Color(0xFFFFC92F).toArgb(),
        Color(0xFF1E74FE).toArgb(),
        Color(0xFFEF4444).toArgb(),
        Color(0xFFFB923C).toArgb(),
        Color(0xFF6366F1).toArgb(),
        Color(0xFFF43F5E).toArgb(),
        Color(0xFFEC4899).toArgb(),
        Color(0xFF6058EB).toArgb(),
    )

    private val _selectedYearMonth = MutableStateFlow(YearMonth.now())
    val selectedYearMonth = _selectedYearMonth.asStateFlow()

    private val _isMonthlyDataLoading = MutableStateFlow(false)
    private val _isCategoryWiseDataLoading = MutableStateFlow(false)
    private val _isRecentTransactionsLoading = MutableStateFlow(false)

    val isLoading = combine(
        repository.isDataLoading,
        _isCategoryWiseDataLoading,
        _isMonthlyDataLoading,
        _isRecentTransactionsLoading
    ) { loading1, loading2, loading3, loading4 ->
        loading1 or loading2 or loading3 or loading4
    }

    fun getRecentTransactions() = repository.getRecentTransactions().onStart {
        _isRecentTransactionsLoading.update { true }
    }.onEach {
        _isRecentTransactionsLoading.update { false }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getMonthWiseExpense() = repository.getMonthWiseExpense().onStart {
        _isMonthlyDataLoading.update { true }
    }.onEach {
        _isMonthlyDataLoading.update { false }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getDataPoints() = getMonthWiseExpense().mapLatest { monthWiseExpenses ->
        monthWiseExpenses.map { item ->
            DataPoint(item.yearMonth.month.value.toFloat(), item.expense.toFloat())
        }
    }.flowOn(Dispatchers.IO).stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun getCategoryWiseExpense() = _selectedYearMonth.flatMapLatest { yearMonth ->
        repository.getCategoryWiseExpenseForMonth(yearMonth)
    }.onStart {
        _isCategoryWiseDataLoading.update { true }
    }.onEach {
        _isCategoryWiseDataLoading.update { false }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun onMonthChanged(yearMonth: YearMonth) {
        _selectedYearMonth.update { yearMonth }
    }

    fun fetchExpenseFromDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchExpensesFromDevice()
        }
    }
}