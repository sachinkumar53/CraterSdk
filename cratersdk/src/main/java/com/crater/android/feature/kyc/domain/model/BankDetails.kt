package com.crater.android.feature.kyc.domain.model

import com.crater.android.core.common.model.Email
import com.crater.android.core.common.model.Name
import com.crater.android.feature.kyc.ui.model.AccountNumber
import com.crater.android.feature.kyc.ui.model.IFSC
import kotlinx.serialization.Serializable

@Serializable
data class BankDetails(
    val accountName: Name,
    val accountNumber: AccountNumber,
    val ifsc: IFSC,
    val email: Email,
)
