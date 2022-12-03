package com.crater.android.feature.invoicing.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Service(
    val name: String,
    val price: String,
    val quantity: Int,
    val sac: String?
) : Parcelable {

    val totalPrice get() = (price.toDoubleOrNull() ?: 0.0) * quantity.toDouble()
}