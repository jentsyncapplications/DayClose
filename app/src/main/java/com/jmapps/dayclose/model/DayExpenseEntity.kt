package com.jmapps.dayclose.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmapps.dayclose.utils.standardDateFormat
import androidx.room.Index
import androidx.room.ForeignKey

//
//@Entity(tableName = "day_expense")
//data class DayExpenseEntity(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val date: String = standardDateFormat(System.currentTimeMillis()), //DD-MM-YYYY
//    val dayId: String,
//    val timestamp: Long = System.currentTimeMillis(),
//    val totalExpense: Float,
//    val expenseCategory: String,
//    val expenseDescription: String?=null
//)


@Entity(
    tableName = "day_expense",
    foreignKeys = [
        ForeignKey(
            entity = DayDataEntity::class,
            parentColumns = ["dayId"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["dayId"]),
        Index(value = ["expenseId"], unique = true)
    ]
)
data class DayExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String = standardDateFormat(System.currentTimeMillis()),
    val dayId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val totalExpense: Float,
    val expenseCategory: String,
    val expenseDescription: String? = null,
    val expenseId: String
)