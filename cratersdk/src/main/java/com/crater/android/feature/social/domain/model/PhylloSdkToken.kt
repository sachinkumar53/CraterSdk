package com.crater.android.feature.social.domain.model

@JvmInline
value class PhylloSdkToken(override val value: String) : StringWrapper

fun StringWrapper?.isNullOrEmpty(): Boolean {
    return this == null || value.isEmpty()
}

interface StringWrapper {
    val value: String
}