package com.crater.android.feature.cash.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccountDetail(
    val availableBalance: Amount,
    val accountNo: String,
    val ifsc: String,
    val upiId: String,
    val upiQr: String
)
