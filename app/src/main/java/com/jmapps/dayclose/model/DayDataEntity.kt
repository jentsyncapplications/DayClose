package com.jmapps.dayclose.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmapps.dayclose.utils.standardDateFormat
import androidx.room.Index


//
//@Entity(tableName = "day_data")
//data class DayDataEntity(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val date: String = standardDateFormat(System.currentTimeMillis()), //DD-MM-YYYY
//    val openingCash: Float,
//    val closingCash: Float,
//    val openedAt: Long = System.currentTimeMillis(),
//    val closedAt: Long? = null,
//    val note: String? = null,
//    val dayId: String,
//    val timestamp: Long = System.currentTimeMillis()
//)

@Entity(
    tableName = "day_data",
    indices = [Index(value = ["dayId"], unique = true)]
)
data class DayDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String = standardDateFormat(System.currentTimeMillis()),
    val openingCash: Float,
    val closingCash: Float? = null,
    val openedAt: Long = System.currentTimeMillis(),
    val closedAt: Long? = null,
    val note: String? = null,
    val dayId: String,
    val timestamp: Long = System.currentTimeMillis()
)