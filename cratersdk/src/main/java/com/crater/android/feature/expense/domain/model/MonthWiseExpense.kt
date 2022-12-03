package com.crater.android.feature.expense.domain.model

import java.time.YearMonth

data class MonthWiseExpense(
    val expense: Double,
    val yearMonth: YearMonth
)