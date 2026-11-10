package com.jmapps.dayclose.utils



import com.jmapps.dayclose.model.DaySummary
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random


private val fakeDateFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd")

fun generateFakeDaySummaries(
    endDate: LocalDate = LocalDate.now(),
    years: Long = 4,
    seed: Int = 42
): List<DaySummary> {

    val random = Random(seed)
    val summaries = mutableListOf<DaySummary>()

    // Start 4 years before the selected end date.
    val startDate = endDate.minusYears(years)

    var currentDate = startDate
    var id = 1

    while (!currentDate.isAfter(endDate)) {

        val isClosed = random.nextFloat() < 0.75f

        val openingCash =
            random.nextInt(5_000, 30_001).toFloat()

        val totalExpense = if (isClosed) {
            random.nextInt(500, 12_001).toFloat()
        } else {
            0f
        }

        val netProfit = if (isClosed) {
            random.nextInt(-5_000, 20_001).toFloat()
        } else {
            null
        }

        val closingCash = if (isClosed) {
            openingCash + totalExpense + netProfit!!
        } else {
            null
        }

        val openedAt = currentDate
            .atTime(8, 0)
            .atZone(java.time.ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val closedAt = if (isClosed) {
            currentDate
                .atTime(18, 0)
                .atZone(java.time.ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        } else {
            null
        }

        summaries.add(
            DaySummary(
                id = id,
                dayId = "fake-day-$id",
                date = currentDate.format(fakeDateFormatter),
                openingCash = openingCash,
                closingCash = closingCash,
                openedAt = openedAt,
                closedAt = closedAt,
                note = "Fake testing data",
                timestamp = openedAt,
                totalExpense = totalExpense,
                netProfit = netProfit
            )
        )

        id++
        currentDate = currentDate.plusDays(1)
    }

    return summaries
}
