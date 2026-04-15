package com.hess.everly.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateUtils {
    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val prettyFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.getDefault())

    fun formatDisplayDate(date: String): String = try {
        LocalDate.parse(date, isoFormatter).format(prettyFormatter)
    } catch (_: Exception) {
        date
    }

    fun isUpcoming(date: String): Boolean = try {
        !LocalDate.parse(date, isoFormatter).isBefore(LocalDate.now())
    } catch (_: Exception) {
        true
    }
}
