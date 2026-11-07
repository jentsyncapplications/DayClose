package com.jmapps.dayclose.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmapps.dayclose.model.DayExpenseEntity
import com.jmapps.dayclose.repository.DayExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * ViewModel
 * - Exposes expense data to the UI
 * - Handles background work using viewModelScope
 * - Communicates with DayExpenseRepository
 */
class DayExpenseViewModel(
    private val repository: DayExpenseRepository
) : ViewModel() {


    /* -------------------- INSERT & UPDATE -------------------- */

    // Add a new expense
    fun addExpense(expense: DayExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
        }
    }

    // Update an existing expense using expenseId
    fun updateExpense(
        expenseId: String,
        totalExpense: Float,
        expenseCategory: String,
        expenseDescription: String? = null
    ) {
        viewModelScope.launch {
            repository.updateExpense(
                expenseId = expenseId,
                totalExpense = totalExpense,
                expenseCategory = expenseCategory,
                expenseDescription = expenseDescription
            )
        }
    }


    /* -------------------- READ OPERATIONS -------------------- */

    // Observe all expenses
    fun getAllExpenses(): Flow<List<DayExpenseEntity>> {
        return repository.getAllExpenses()
    }

    // Observe expenses for a specific business day
    fun getExpensesByDayId(dayId: String): Flow<List<DayExpenseEntity>> {
        return repository.getExpensesByDayId(dayId)
    }

    // Observe a single expense using expenseId
    fun getExpenseById(expenseId: String): Flow<DayExpenseEntity?> {
        return repository.getExpenseById(expenseId)
    }

    // Observe expenses for a specific date
    fun getExpensesByDate(date: String): Flow<List<DayExpenseEntity>> {
        return repository.getExpensesByDate(date)
    }


    /* -------------------- EXPENSE TOTALS -------------------- */

    // Observe total expenses for a specific business day
    fun getTotalExpensesByDayId(dayId: String): Flow<Float> {
        return repository.getTotalExpensesByDayId(dayId)
    }

    // Observe total expenses for a specific date
    fun getTotalExpensesByDate(date: String): Flow<Float> {
        return repository.getTotalExpensesByDate(date)
    }

    // Observe total expenses for a category and business day
    fun getTotalExpensesByCategory(
        dayId: String,
        category: String
    ): Flow<Float> {
        return repository.getTotalExpensesByCategory(dayId, category)
    }


    /* -------------------- DELETE -------------------- */

    // Delete an expense using expenseId
    fun deleteExpense(expenseId: String) {
        viewModelScope.launch {
            repository.deleteExpense(expenseId)
        }
    }

    // Delete all expenses belonging to a specific business day
    fun deleteExpensesByDayId(dayId: String) {
        viewModelScope.launch {
            repository.deleteExpensesByDayId(dayId)
        }
    }

}