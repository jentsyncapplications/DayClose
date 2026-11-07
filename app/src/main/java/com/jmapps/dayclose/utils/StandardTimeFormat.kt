package com.jmapps.dayclose.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun standardTimeFormat(timestamp: Long): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault())
        .format(Date(timestamp))
}