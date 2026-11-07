package com.jmapps.dayclose.repository

import com.jmapps.dayclose.data.local_data.DayExpenseDao
import com.jmapps.dayclose.model.DayExpenseEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository layer
 * - Acts as a bridge between DAO and ViewModel
 * - Keeps expense database logic out of the ViewModel
 * - Handles adding, editing and retrieving expenses
 */
class DayExpenseRepository(
    private val dayExpenseDao: DayExpenseDao
) {

    /* -------------------- INSERT & UPDATE -------------------- */

    // Add a new expense to a specific business day
    suspend fun addExpense(expense: DayExpenseEntity): Long {
        return dayExpenseDao.addExpense(expense)
    }

    // Edit an existing expense using expenseId
    suspend fun updateExpense(
        expenseId: String,
        totalExpense: Float,
        expenseCategory: String,
        expenseDescription: String?
    ): Int {
        return dayExpenseDao.updateExpense(
            expenseId = expenseId,
            totalExpense = totalExpense,
            expenseCategory = expenseCategory,
            expenseDescription = expenseDescription
        )
    }


    /* -------------------- READ OPERATIONS -------------------- */

    // Get all expenses
    fun getAllExpenses(): Flow<List<DayExpenseEntity>> {
        return dayExpenseDao.getAllExpenses()
    }

    // Get expenses belonging to a specific business day
    fun getExpensesByDayId(dayId: String): Flow<List<DayExpenseEntity>> {
        return dayExpenseDao.getExpensesByDayId(dayId)
    }

    // Get a single expense using expenseId
    fun getExpenseById(expenseId: String): Flow<DayExpenseEntity?> {
        return dayExpenseDao.getExpenseById(expenseId)
    }

    // Get expenses for a specific date
    fun getExpensesByDate(date: String): Flow<List<DayExpenseEntity>> {
        return dayExpenseDao.getExpensesByDate(date)
    }


    /* -------------------- EXPENSE TOTALS -------------------- */

    // Get total expenses for a specific business day
    fun getTotalExpensesByDayId(dayId: String): Flow<Float> {
        return dayExpenseDao.getTotalExpensesByDayId(dayId)
    }

    // Get total expenses for a specific date
    fun getTotalExpensesByDate(date: String): Flow<Float> {
        return dayExpenseDao.getTotalExpensesByDate(date)
    }

    // Get total expenses for a specific category and business day
    fun getTotalExpensesByCategory(
        dayId: String,
        category: String
    ): Flow<Float> {
        return dayExpenseDao.getTotalExpensesByCategory(dayId, category)
    }


    /* -------------------- DELETE -------------------- */

    // Delete an expense using expenseId
    suspend fun deleteExpense(expenseId: String): Int {
        return dayExpenseDao.deleteExpenseById(expenseId)
    }

    // Delete all expenses belonging to a specific business day
    suspend fun deleteExpensesByDayId(dayId: String): Int {
        return dayExpenseDao.deleteExpensesByDayId(dayId)
    }

}