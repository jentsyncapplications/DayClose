package com.jmapps.dayclose.utils

fun getGreeting(): String {
    val hour = java.time.LocalTime.now().hour

    return when (hour) {
        in 5..11 -> "Good morning!"
        in 12..16 -> "Good afternoon!"
        in 17..20 -> "Good evening!"
        else -> "Good night!"
    }
}
