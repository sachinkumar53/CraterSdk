package com.crater.android.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

object FormatterUtil {

    fun formatQuantity(quantity: Int?): String {
        quantity ?: return "0"
        return if (quantity >= 1000) {
            val k = quantity.toFloat() / 1000f
            val s = DecimalFormat("0.#").format(k)
            "${s}k"
        } else quantity.toString()
    }

    fun formatHours(hrs: Double?): String {
        hrs ?: return "0"
        //val mins = hrs.toFloat() * 60f
        return DecimalFormat("0.#").format(hrs) + "hr"
    }

    fun formatMinutes(hrs: Double?): String {
        hrs ?: return "0"
        val mins = hrs.toFloat() * 60f
        return if (mins > 0.0) DecimalFormat("0.#").format(mins) + " minutes" else "0"
    }

    fun getFormattedNumber(count: Int?): String {
        return if (count == null) "0"
        else getFormattedNumber(count.toFloat())
    }

    fun getFormattedNumber(count: Float): String {
        if (count < 1000) return DecimalFormat("0.#").format(count).toString()
        val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f %c", count / 1000.0.pow(exp.toDouble()), "kMGTPE"[exp - 1])
    }

    private val currencyFormat = NumberFormat.getCurrencyInstance(
        Locale("en", "in")
    ).apply {
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }

    fun getFormattedAmount(amount: Double): String {
        return currencyFormat.format(amount)
    }

}