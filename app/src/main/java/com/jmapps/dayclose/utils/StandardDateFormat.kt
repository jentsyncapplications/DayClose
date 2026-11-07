package com.jmapps.dayclose.utils


import android.icu.text.SimpleDateFormat
import java.util.*

fun standardDateFormat(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(timestamp))
}