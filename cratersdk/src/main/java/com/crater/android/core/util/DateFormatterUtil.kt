package com.crater.android.core.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DateFormatterUtil {
    private const val MEDIUM_DATE_FORMAT = "dd MMMM yyyy"
    private const val SHORT_DATE_FORMAT = "dd/MM/yy"

    /**
     * Formats the provided LocalDate in medium date format style
     * Ex 25 November 2022
     */
    fun formatInMediumStyle(date: LocalDate): String {
        return formatInStyle(date, MEDIUM_DATE_FORMAT)
    }

    /**
     * Formats the provided LocalDate in short date format style
     * Ex 25/11/22
     */
    fun formatInShortStyle(date: LocalDate): String {
        return formatInStyle(date, SHORT_DATE_FORMAT)
    }

    private fun formatInStyle(
        date: LocalDate,
        pattern: String
    ): String {
        return date.format(DateTimeFormatter.ofPattern(pattern))
    }
}