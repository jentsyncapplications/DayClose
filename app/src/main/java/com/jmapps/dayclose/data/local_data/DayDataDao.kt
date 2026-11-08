package com.jmapps.dayclose.data.local_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.jmapps.dayclose.model.DayDataEntity
import com.jmapps.dayclose.model.DaySummary


// This interface defines all database operations
// related to business day records.
@Dao
interface DayDataDao {

    // --------------------------------------------------
    // DAY OPENING OPERATIONS
    // --------------------------------------------------

    // Open a new business day by saving opening cash.
    // Each day must have a unique dayId.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun openDay(dayData: DayDataEntity): Long


    // Update opening cash using the unique dayId.
    @Query("""
        UPDATE day_data
        SET openingCash = :openingCash
        WHERE dayId = :dayId
    """)
    suspend fun updateOpeningCash(
        dayId: String,
        openingCash: Float
    ): Int


    // --------------------------------------------------
    // DAY CLOSING OPERATIONS
    // --------------------------------------------------

    // Add closing cash when the business day is closed.
    // Also record the closing timestamp.
    @Query("""
        UPDATE day_data
        SET closingCash = :closingCash,
            closedAt = :closedAt
        WHERE dayId = :dayId
    """)
    suspend fun closeDay(
        dayId: String,
        closingCash: Float,
        closedAt: Long = System.currentTimeMillis()
    ): Int


    // Edit closing cash using the unique dayId.
    @Query("""
        UPDATE day_data
        SET closingCash = :closingCash
        WHERE dayId = :dayId
    """)
    suspend fun updateClosingCash(
        dayId: String,
        closingCash: Float
    ): Int


    // --------------------------------------------------
    // RETRIEVING DAY DATA
    // --------------------------------------------------

    // Get one business day using its unique dayId.
    @Query("""
        SELECT * FROM day_data
        WHERE dayId = :dayId
        LIMIT 1
    """)
    fun getDayById(dayId: String): Flow<DayDataEntity?>


    // Get all business days.
    // Latest business day appears first.
    @Query("""
        SELECT * FROM day_data
        ORDER BY timestamp DESC
    """)
    fun getAllDays(): Flow<List<DayDataEntity>>


    // Get business day records for a specific date.
    // Example: 07-11-2026
    @Query("""
        SELECT * FROM day_data
        WHERE date = :date
        ORDER BY timestamp DESC
    """)
    fun getDaysByDate(date: String): Flow<List<DayDataEntity>>


    // Get all days within a specific date range.
    // Useful for daily reports and filtering.
    @Query("""
        SELECT * FROM day_data
        WHERE timestamp BETWEEN :startTimestamp AND :endTimestamp
        ORDER BY timestamp DESC
    """)
    fun getDaysByDateRange(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DayDataEntity>>


    // --------------------------------------------------
    // DAILY REPORTS AND SUMMARIES
    // --------------------------------------------------

    // Get daily business summaries.
    // Includes opening cash, closing cash,
    // total expenses and net profit.
    //
    // LEFT JOIN ensures that days without expenses
    // are also included in the report.
    @Query("""
        SELECT
            d.id,
            d.dayId,
            d.date,
            d.openingCash,
            d.closingCash,
            d.openedAt,
            d.closedAt,
            d.note,
            d.timestamp,

            COALESCE(SUM(e.totalExpense), 0.0) AS totalExpense,

            CASE
                WHEN d.closingCash IS NULL THEN NULL
                ELSE d.closingCash - d.openingCash - COALESCE(SUM(e.totalExpense), 0.0)
            END AS netProfit

        FROM day_data AS d

        LEFT JOIN day_expense AS e
            ON d.dayId = e.dayId

        GROUP BY d.id

        ORDER BY d.timestamp DESC
    """)
    fun getAllDaySummaries(): Flow<List<DaySummary>>


    // Get daily summaries for a specific date.
    // Example: 07-11-2026
    @Query("""
        SELECT
            d.id,
            d.dayId,
            d.date,
            d.openingCash,
            d.closingCash,
            d.openedAt,
            d.closedAt,
            d.note,
            d.timestamp,

            COALESCE(SUM(e.totalExpense), 0.0) AS totalExpense,

            CASE
                WHEN d.closingCash IS NULL THEN NULL
                ELSE d.closingCash - d.openingCash - COALESCE(SUM(e.totalExpense), 0.0)
            END AS netProfit

        FROM day_data AS d

        LEFT JOIN day_expense AS e
            ON d.dayId = e.dayId

        WHERE d.date = :date

        GROUP BY d.id

        ORDER BY d.timestamp DESC
    """)
    fun getDaySummariesByDate(date: String): Flow<List<DaySummary>>


    // Get a summary for one specific business day.
    @Query("""
        SELECT
            d.id,
            d.dayId,
            d.date,
            d.openingCash,
            d.closingCash,
            d.openedAt,
            d.closedAt,
            d.note,
            d.timestamp,

            COALESCE(SUM(e.totalExpense), 0.0) AS totalExpense,

            CASE
                WHEN d.closingCash IS NULL THEN NULL
                ELSE d.closingCash - d.openingCash - COALESCE(SUM(e.totalExpense), 0.0)
            END AS netProfit

        FROM day_data AS d

        LEFT JOIN day_expense AS e
            ON d.dayId = e.dayId

        WHERE d.dayId = :dayId

        GROUP BY d.id

        LIMIT 1
    """)
    fun getDaySummaryById(dayId: String): Flow<DaySummary?>






    // Get a summary for one specific business day by date.
// Returns zero values when no business day exists or closing cash has not been added.
    @Query("""
    SELECT
        COALESCE(d.id, 0) AS id,
        COALESCE(d.dayId, '') AS dayId,
        :dayDate AS date,
        COALESCE(d.openingCash, 0.0) AS openingCash,
        COALESCE(d.closingCash, 0.0) AS closingCash,
        COALESCE(d.openedAt, 0) AS openedAt,
        d.closedAt AS closedAt,
        d.note AS note,
        COALESCE(d.timestamp, 0) AS timestamp,

        COALESCE(SUM(e.totalExpense), 0.0) AS totalExpense,

        COALESCE(d.closingCash, 0.0)
            - COALESCE(d.openingCash, 0.0)
            - COALESCE(SUM(e.totalExpense), 0.0) AS netProfit

    FROM (SELECT :dayDate AS requestedDate) AS requested

    LEFT JOIN day_data AS d
        ON d.date = requested.requestedDate

    LEFT JOIN day_expense AS e
        ON d.dayId = e.dayId

    GROUP BY d.id

    LIMIT 1
""")
    fun getDaySummaryByDate(dayDate: String): Flow<DaySummary>


    // --------------------------------------------------
    // DELETE OPERATIONS
    // --------------------------------------------------

    // Delete a business day using its unique dayId.
    // Associated expenses are deleted automatically
    // because of the CASCADE foreign key.
    @Query("""
        DELETE FROM day_data
        WHERE dayId = :dayId
    """)
    suspend fun deleteDayById(dayId: String): Int

}