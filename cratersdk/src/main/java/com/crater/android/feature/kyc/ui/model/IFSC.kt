package com.crater.android.feature.kyc.ui.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class IFSC(val code: String) {

    fun validate(): Boolean {
        if (code.isBlank()) return false
        return IFSC_CODE_PATTERN.toPattern().matcher(code).matches()
    }

    companion object {
        const val IFSC_CODE_LENGTH = 11
        private const val IFSC_CODE_PATTERN = "^[A-Za-z]{4}[0][A-Za-z0-9]{6}$"
    }
}