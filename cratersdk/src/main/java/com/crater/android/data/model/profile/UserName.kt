package com.crater.android.data.model.profile

import kotlinx.serialization.Serializable

@Serializable
data class UserName(
    val firstName: String,
    val lastName: String
) {
    val name get() = "${firstName.trim()} ${lastName.trim()}"
}
