package com.jmapps.dayclose.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

//ChartData


data class ChartPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate
)


data class ChartPoint(
    val slot: Int,
    val label: String,
    val value: Float
)

data class ChartData(
    val points: List<ChartPoint>,
    val slotCount: Int,
    val axisLabels: List<String>
)


// Parse the date stored in DaySummary
private val summaryDateFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun parseSummaryDate(date: String): LocalDate? {
    return runCatching {
        LocalDate.parse(date, summaryDateFormatter)
    }.getOrNull()
}
