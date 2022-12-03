package com.crater.android.feature.cash.ui.model

import com.crater.android.feature.cash.domain.model.Amount

data class ResultSuccessArgs(
    val amount: Amount,
    val flowType: FlowType
)