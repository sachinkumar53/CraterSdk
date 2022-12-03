package com.crater.android.core.common.model

import androidx.core.util.PatternsCompat
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Email(val id: String) {

    fun validate(): Boolean {
        if (id.isBlank()) return false
        return PatternsCompat.EMAIL_ADDRESS.matcher(id).matches()
    }
}