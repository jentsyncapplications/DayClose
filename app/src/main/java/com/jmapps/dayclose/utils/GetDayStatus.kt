package com.jmapps.dayclose.utils

import com.jmapps.dayclose.model.DayStatus
import com.jmapps.dayclose.model.DaySummary

fun getDayStatus(summary: DaySummary): DayStatus {
    return when {
        summary.dayId.isEmpty() -> DayStatus.NOT_OPENED
        summary.closedAt == null -> DayStatus.OPEN
        else -> DayStatus.CLOSED
    }
}