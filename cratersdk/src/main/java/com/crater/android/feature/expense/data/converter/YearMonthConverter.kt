package com.crater.android.feature.expense.data.converter

import androidx.room.TypeConverter
import java.time.YearMonth

class YearMonthConverter {
    @TypeConverter
    fun fromYearMonthToString(yearMonth: YearMonth): String {
        return yearMonth.toString()
    }

    @TypeConverter
    fun fromStringToYearMonth(str: String): YearMonth {
        return YearMonth.parse(str)
    }
}