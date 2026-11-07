package com.jmapps.dayclose.model



data class DaySummary(

    val id: Int,

    val dayId: String,

    val date: String,

    val openingCash: Float,

    val closingCash: Float?,

    val openedAt: Long,

    val closedAt: Long?,

    val note: String?,

    val timestamp: Long,

    val totalExpense: Float,

    val netProfit: Float?

)