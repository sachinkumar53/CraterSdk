package com.crater.android.feature.expense.data.converter

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateConverter {

    @TypeConverter
    fun fromLocalDateToString(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun fromStringToLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }
}