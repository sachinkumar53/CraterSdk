package com.crater.android.feature.expense.domain.model

import com.crater.android.feature.expense.domain.model.ExpenseCategory

data class CategoryWiseExpense(
    val expense: Double,
    val category: ExpenseCategory
)