package com.jmapps.dayclose.repository


import com.jmapps.dayclose.data.local_data.DayDataDao
import com.jmapps.dayclose.model.DayDataEntity
import com.jmapps.dayclose.model.DaySummary
import kotlinx.coroutines.flow.Flow

/**
 * Repository layer
 * - Acts as a bridge between DAO and ViewModel
 * - Keeps database logic out of the ViewModel
 * - Handles business day operations and daily reports
 */
class DayDataRepository(
    private val dayDataDao: DayDataDao
) {

    /* -------------------- DAY OPENING -------------------- */

    // Open a new business day by inserting opening cash
    suspend fun openDay(dayData: DayDataEntity): Long {
        return dayDataDao.openDay(dayData)
    }

    // Update opening cash using dayId
    suspend fun updateOpeningCash(
        dayId: String,
        openingCash: Float
    ): Int {
        return dayDataDao.updateOpeningCash(dayId, openingCash)
    }


    /* -------------------- DAY CLOSING -------------------- */

    // Close a business day by recording closing cash
    suspend fun closeDay(
        dayId: String,
        closingCash: Float,
        closedAt: Long = System.currentTimeMillis()
    ): Int {
        return dayDataDao.closeDay(dayId, closingCash, closedAt)
    }

    // Edit closing cash using dayId
    suspend fun updateClosingCash(
        dayId: String,
        closingCash: Float
    ): Int {
        return dayDataDao.updateClosingCash(dayId, closingCash)
    }


    /* -------------------- READ OPERATIONS -------------------- */

    // Get a business day using its unique dayId
    fun getDayById(dayId: String): Flow<DayDataEntity?> {
        return dayDataDao.getDayById(dayId)
    }

    // Get all business days
    fun getAllDays(): Flow<List<DayDataEntity>> {
        return dayDataDao.getAllDays()
    }

    // Get business days for a specific date
    fun getDaysByDate(date: String): Flow<List<DayDataEntity>> {
        return dayDataDao.getDaysByDate(date)
    }

    // Get business days within a timestamp range
    fun getDaysByDateRange(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DayDataEntity>> {
        return dayDataDao.getDaysByDateRange(startTimestamp, endTimestamp)
    }


    /* -------------------- DAILY REPORTS -------------------- */

    // Get all business-day summaries
    fun getAllDaySummaries(): Flow<List<DaySummary>> {
        return dayDataDao.getAllDaySummaries()
    }

    // Get summaries for a specific date
    fun getDaySummariesByDate(date: String): Flow<List<DaySummary>> {
        return dayDataDao.getDaySummariesByDate(date)
    }

    // Get summary using dayId
    fun getDaySummaryById(dayId: String): Flow<DaySummary?> {
        return dayDataDao.getDaySummaryById(dayId)
    }


    // Get summary for a specific date.
    fun getDaySummaryByDate(dayDate: String): Flow<DaySummary> {
        return dayDataDao.getDaySummaryByDate(dayDate)
    }

    /* -------------------- DELETE -------------------- */

    // Delete a business day using dayId
    // Associated expenses are deleted through CASCADE
    suspend fun deleteDay(dayId: String): Int {
        return dayDataDao.deleteDayById(dayId)
    }

}