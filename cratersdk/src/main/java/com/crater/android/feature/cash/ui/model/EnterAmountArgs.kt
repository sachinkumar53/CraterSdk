package com.crater.android.feature.cash.ui.model

data class EnterAmountArgs(
    val accountName: String,
    val accountId: String = "",
    val flowType: FlowType
)