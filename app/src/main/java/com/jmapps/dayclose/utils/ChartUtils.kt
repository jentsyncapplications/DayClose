package com.jmapps.dayclose.utils

import com.jmapps.dayclose.model.ChartData
import com.jmapps.dayclose.model.ChartPeriod
import com.jmapps.dayclose.model.ChartPoint
import com.jmapps.dayclose.model.DaySummary
import com.jmapps.dayclose.model.parseSummaryDate
import com.jmapps.dayclose.ui_screens.TimeRange
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import kotlin.collections.mapNotNull
import java.time.format.DateTimeFormatter



fun getChartPeriod(
    range: TimeRange,
    selectedDate: LocalDate,
    firstUseDate: LocalDate
): ChartPeriod {

    return when (range) {

        TimeRange.WEEKLY -> {
            // Each user's first recorded business day anchors their weeks
            val daysSinceStart = ChronoUnit.DAYS.between(
                firstUseDate,
                selectedDate
            )

            val weekNumber = Math.floorDiv(daysSinceStart, 7L)

            val weekStart = firstUseDate.plusDays(weekNumber * 7)

            ChartPeriod(
                startDate = weekStart,
                endDate = weekStart.plusDays(6)
            )
        }

        TimeRange.MONTHLY -> {
            // Normal calendar month
            val month = YearMonth.from(selectedDate)

            ChartPeriod(
                startDate = month.atDay(1),
                endDate = month.atEndOfMonth()
            )
        }

        TimeRange.YEARLY -> {
            // Normal calendar year
            ChartPeriod(
                startDate = selectedDate.withDayOfYear(1),
                endDate = selectedDate.withDayOfYear(
                    selectedDate.lengthOfYear()
                )
            )
        }
    }
}



fun getChartData(
    summaries: List<DaySummary>,
    range: TimeRange,
    selectedDate: LocalDate,
    firstUseDate: LocalDate
): ChartData {

    // Only closed days with valid dates.
    // Calculate Estimated Cash Inflow instead of Net Profit.
    val closedDays = summaries.mapNotNull { summary ->

        val closingCash = summary.closingCash
            ?: return@mapNotNull null

        val date = parseSummaryDate(summary.date)
            ?: return@mapNotNull null

        val estimatedCashInflow =
            closingCash - summary.openingCash + summary.totalExpense

        date to estimatedCashInflow
    }

    // Combine estimated cash inflow for entries
    // belonging to the same date.
    val dailyTotals = closedDays
        .groupBy { it.first }
        .mapValues { (_, entries) ->
            entries.sumOf { it.second.toDouble() }.toFloat()
        }

    return when (range) {

        // --------------------------------
        // WEEKLY: 7 days
        // --------------------------------
        TimeRange.WEEKLY -> {

            val period = getChartPeriod(
                range,
                selectedDate,
                firstUseDate
            )

            val labels = (0..6).map { index ->
                period.startDate
                    .plusDays(index.toLong())
                    .format(DateTimeFormatter.ofPattern("EEE"))
            }

            val points = (0..6).mapNotNull { slot ->

                val date = period.startDate.plusDays(slot.toLong())

                dailyTotals[date]?.let { inflow ->

                    ChartPoint(
                        slot = slot,
                        label = labels[slot],
                        value = inflow
                    )
                }
            }

            ChartData(
                points = points,
                slotCount = 7,
                axisLabels = labels
            )
        }

        // --------------------------------
        // MONTHLY: 28, 29, 30 or 31 days
        // --------------------------------
        TimeRange.MONTHLY -> {

            val month = YearMonth.from(selectedDate)

            val daysInMonth = month.lengthOfMonth()

            val labels = (1..daysInMonth).map {
                it.toString()
            }

            val points = (1..daysInMonth).mapNotNull { day ->

                val date = month.atDay(day)

                dailyTotals[date]?.let { inflow ->

                    ChartPoint(
                        slot = day - 1,
                        label = day.toString(),
                        value = inflow
                    )
                }
            }

            ChartData(
                points = points,
                slotCount = daysInMonth,
                axisLabels = labels
            )
        }

        // --------------------------------
        // YEARLY: 12 months
        // --------------------------------
        TimeRange.YEARLY -> {

            val year = selectedDate.year

            // Add daily estimated inflows into monthly totals.
            val monthlyTotals = dailyTotals
                .filterKeys { it.year == year }
                .entries
                .groupBy { YearMonth.from(it.key) }
                .mapValues { (_, entries) ->
                    entries.sumOf {
                        it.value.toDouble()
                    }.toFloat()
                }

            val labels = (1..12).map { month ->
                YearMonth.of(year, month)
                    .format(DateTimeFormatter.ofPattern("MMM"))
            }

            val points = (1..12).mapNotNull { month ->

                val yearMonth = YearMonth.of(year, month)

                monthlyTotals[yearMonth]?.let { inflow ->

                    ChartPoint(
                        slot = month - 1,
                        label = labels[month - 1],
                        value = inflow
                    )
                }
            }

            ChartData(
                points = points,
                slotCount = 12,
                axisLabels = labels
            )
        }
    }
}
