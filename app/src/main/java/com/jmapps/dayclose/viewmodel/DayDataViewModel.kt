package com.jmapps.dayclose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmapps.dayclose.model.DayDataEntity
import com.jmapps.dayclose.model.DaySummary
import com.jmapps.dayclose.repository.DayDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel
 * - Exposes business day data to the UI
 * - Handles background work using viewModelScope
 * - Communicates with DayDataRepository
 */
class DayDataViewModel(
    private val repository: DayDataRepository
) : ViewModel() {


    /* -------------------- DAY OPENING -------------------- */

    // Open a new business day
    fun openDay(dayData: DayDataEntity) {
        viewModelScope.launch {
            repository.openDay(dayData)
        }
    }

    // Update opening cash using dayId
    fun updateOpeningCash(
        dayId: String,
        openingCash: Float
    ) {
        viewModelScope.launch {
            repository.updateOpeningCash(dayId, openingCash)
        }
    }


    /* -------------------- DAY CLOSING -------------------- */

    // Close a business day
    fun closeDay(
        dayId: String,
        closingCash: Float
    ) {
        viewModelScope.launch {
            repository.closeDay(dayId, closingCash)
        }
    }

    // Edit closing cash using dayId
    fun updateClosingCash(
        dayId: String,
        closingCash: Float
    ) {
        viewModelScope.launch {
            repository.updateClosingCash(dayId, closingCash)
        }
    }


    /* -------------------- READ OPERATIONS -------------------- */

    // Observe a business day using dayId
    fun getDayById(dayId: String): Flow<DayDataEntity?> {
        return repository.getDayById(dayId)
    }

    // Observe all business days
    fun getAllDays(): Flow<List<DayDataEntity>> {
        return repository.getAllDays()
    }

    // Observe business days for a specific date
    fun getDaysByDate(date: String): Flow<List<DayDataEntity>> {
        return repository.getDaysByDate(date)
    }

    // Observe business days within a timestamp range
    fun getDaysByDateRange(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<DayDataEntity>> {
        return repository.getDaysByDateRange(startTimestamp, endTimestamp)
    }


    /* -------------------- DAILY REPORTS -------------------- */

    // Observe all business-day summaries
    fun getAllDaySummaries(): Flow<List<DaySummary>> {
        return repository.getAllDaySummaries()
    }

    // Observe summaries for a specific date
    fun getDaySummariesByDate(date: String): Flow<List<DaySummary>> {
        return repository.getDaySummariesByDate(date)
    }

    // Observe a business-day summary using dayId
    fun getDaySummaryById(dayId: String): Flow<DaySummary?> {
        return repository.getDaySummaryById(dayId)
    }


    /* -------------------- DELETE -------------------- */

    // Delete a business day
    fun deleteDay(dayId: String) {
        viewModelScope.launch {
            repository.deleteDay(dayId)
        }
    }

}