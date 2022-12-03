package com.crater.android.core.common.model

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Name(val name: String) {


    fun validate(): Boolean {
//        if (name.isBlank()) return false
//        return NAME_PATTERN.toPattern().matcher(name).matches()
        return name.isNotBlank()
    }

    companion object {
        //private const val NAME_PATTERN = "^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}"
    }
}