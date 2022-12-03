package com.crater.android.feature.kyc.domain.repository

import com.crater.android.feature.kyc.domain.model.BankDetails
import com.crater.android.feature.kyc.domain.model.PanNumber

interface VerificationRepository {
    suspend fun verifyPanNumber(panNumber: PanNumber): Boolean
    suspend fun verifyBankDetails(details: BankDetails): Boolean
}