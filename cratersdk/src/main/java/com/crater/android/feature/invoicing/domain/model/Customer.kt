package com.crater.android.feature.invoicing.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
//    val id: Int,
    val name: String,
    val phone: String,
    val emailId: String?,
    val gst: String?,
    val city: String?,
    val pinCode: String?,
//    val userId: String?,
    val pan: String?,
    val address: String?,
    val state: String?,
    val country: String?
) : Parcelable {

    constructor() : this(
        name = "",
        phone = "",
        emailId = null,
        gst = null,
        city = null,
        pinCode = null,
        pan = null,
        address = null,
        state = null,
        country = null
    )

    fun getFormattedAddress(): String {
        return sequenceOf(address, city, state, pinCode, country).filterNot {
            it.isNullOrBlank()
        }.joinToString(", ")
    }
}
