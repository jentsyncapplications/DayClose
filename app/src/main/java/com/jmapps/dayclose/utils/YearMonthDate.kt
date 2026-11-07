package com.jmapps.dayclose.utils



import java.time.LocalDate
import java.util.Locale

fun yearMonthDate(date: String): String? {
    return try {
        val parsed = LocalDate.parse(date)
        "${parsed.year}-${String.format(Locale.US, "%02d", parsed.monthValue)}"
    } catch (e: Exception) {
        null // or return ""
    }
}
