package com.jmapps.dayclose.data.local_data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import com.jmapps.dayclose.model.DayExpenseEntity


// This interface defines all database operations
// related to business day expenses.
@Dao
interface DayExpenseDao {


    // --------------------------------------------------
    // ADD EXPENSE OPERATIONS
    // --------------------------------------------------

    // Add a new expense to a specific business day.
    // The dayId connects the expense to its business day.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addExpense(expense: DayExpenseEntity): Long


    // --------------------------------------------------
    // EDIT EXPENSE OPERATIONS
    // --------------------------------------------------

    // Edit an existing expense using its unique expenseId.
    @Query("""
        UPDATE day_expense
        SET totalExpense = :totalExpense,
            expenseCategory = :expenseCategory,
            expenseDescription = :expenseDescription
        WHERE expenseId = :expenseId
    """)
    suspend fun updateExpense(
        expenseId: String,
        totalExpense: Float,
        expenseCategory: String,
        expenseDescription: String? = null
    ): Int


    // --------------------------------------------------
    // RETRIEVING EXPENSES
    // --------------------------------------------------

    // Get all expenses.
    // Latest expenses appear first.
    @Query("""
        SELECT * FROM day_expense
        ORDER BY timestamp DESC
    """)
    fun getAllExpenses(): Flow<List<DayExpenseEntity>>


    // Get all expenses belonging to a specific business day.
    @Query("""
        SELECT * FROM day_expense
        WHERE dayId = :dayId
        ORDER BY timestamp DESC
    """)
    fun getExpensesByDayId(dayId: String): Flow<List<DayExpenseEntity>>


    // Get one expense using its unique expenseId.
    @Query("""
        SELECT * FROM day_expense
        WHERE expenseId = :expenseId
        LIMIT 1
    """)
    fun getExpenseById(expenseId: String): Flow<DayExpenseEntity?>


    // Get all expenses recorded on a specific date.
    // Example: 07-11-2026
    @Query("""
        SELECT * FROM day_expense
        WHERE date = :date
        ORDER BY timestamp DESC
    """)
    fun getExpensesByDate(date: String): Flow<List<DayExpenseEntity>>


    // --------------------------------------------------
    // EXPENSE TOTALS AND CALCULATIONS
    // --------------------------------------------------

    // Get total expenses for a particular business day.
    // Returns 0.0 when the day has no expenses.
    @Query("""
        SELECT COALESCE(SUM(totalExpense), 0.0)
        FROM day_expense
        WHERE dayId = :dayId
    """)
    fun getTotalExpensesByDayId(dayId: String): Flow<Float>


    // Get total expenses for a particular date.
    @Query("""
        SELECT COALESCE(SUM(totalExpense), 0.0)
        FROM day_expense
        WHERE date = :date
    """)
    fun getTotalExpensesByDate(date: String): Flow<Float>


    // Get total expenses for a specific category.
    @Query("""
        SELECT COALESCE(SUM(totalExpense), 0.0)
        FROM day_expense
        WHERE dayId = :dayId
        AND expenseCategory = :category
    """)
    fun getTotalExpensesByCategory(
        dayId: String,
        category: String
    ): Flow<Float>


    // --------------------------------------------------
    // DELETE OPERATIONS
    // --------------------------------------------------

    // Delete an expense using its unique expenseId.
    @Query("""
        DELETE FROM day_expense
        WHERE expenseId = :expenseId
    """)
    suspend fun deleteExpenseById(expenseId: String): Int


    // Delete all expenses belonging to a specific business day.
    @Query("""
        DELETE FROM day_expense
        WHERE dayId = :dayId
    """)
    suspend fun deleteExpensesByDayId(dayId: String): Int

}