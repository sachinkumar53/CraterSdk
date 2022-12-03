package com.crater.android.feature.cash.domain.model

import kotlinx.serialization.Serializable
import java.text.NumberFormat
import java.util.*
import kotlin.math.absoluteValue

@Serializable
@JvmInline
value class Amount(val value: Double) {

    operator fun div(other: Amount): Amount {
        return Amount(this.value / other.value)
    }

    operator fun plus(other: Amount): Amount {
        return Amount(this.value + other.value)
    }

    val displayValue: String
        get() = formatter.format(value)

    val absDisplayValue: String
        get() = formatter.format(value.absoluteValue)

    companion object {
        val formatter: NumberFormat by lazy {
            NumberFormat.getCurrencyInstance().apply {
                minimumFractionDigits = 0
                maximumFractionDigits = 2
                currency = Currency.getInstance("INR")
            }
        }
    }
}