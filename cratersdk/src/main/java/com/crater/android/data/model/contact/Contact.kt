package com.crater.android.data.model.contact

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val id: String,
    val displayName: String?,
    val phoneNumbers: List<String>,
    val emails: List<String>
)