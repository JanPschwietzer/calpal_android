package com.janpschwietzer.calpal.util.extensions

import androidx.room.TypeConverter
import java.time.LocalDate


object LocalDateConverter {
    @TypeConverter
    fun toLocalDate(timestamp: Long?): LocalDate? {
        return if (timestamp == null) null else LocalDate.ofEpochDay(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }
}