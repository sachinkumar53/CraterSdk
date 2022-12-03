package com.crater.android.feature.kyc.domain.model

@JvmInline
value class PanNumber(
    val value: String
) {

    fun validate(): Boolean {
        if (value.isBlank() || value.length != PAN_NUMBER_LENGTH) return false
        val pattern = PAN_NUMBER_PATTERN.toPattern()
        return pattern.matcher(value).matches()
    }

    companion object {
        const val PAN_NUMBER_LENGTH = 10
        private const val PAN_NUMBER_PATTERN = "[A-Z]{5}[0-9]{4}[A-Z]"
    }
}