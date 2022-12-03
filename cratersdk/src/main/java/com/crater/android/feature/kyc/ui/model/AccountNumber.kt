package com.crater.android.feature.kyc.ui.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class AccountNumber(val accNo: String) {

    fun validate(): Boolean {
        if (accNo.isBlank()) return false
        return ACCOUNT_NUMBER_PATTERN.toPattern().matcher(accNo).matches()
    }

    companion object {
        const val ACCOUNT_NUMBER_LENGTH = 18
        private const val ACCOUNT_NUMBER_PATTERN = "[0-9]{9,18}"
    }
}