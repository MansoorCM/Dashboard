package com.example.openinapp.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// convert date from yyyy-mm-dd-time format to dd mmm yyyy
fun formatDateYMDTtoDMY(dateString: String): String {
    return getFormattedDate(
        dateString,
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "dd MMM yyyy"
    )
}

// convert date from yyyy-mm-dd format to dd mmm
fun formatDateYMDtoDM(dateString: String): String {
    return getFormattedDate(
        dateString,
        "MM-dd",
        "dd MMM"
    )
}

fun getFormattedDate(
    dateString: String,
    inputFormatString: String,
    outputFormatString: String
): String {
    val inputFormat = SimpleDateFormat(inputFormatString, Locale.getDefault())
    val outputFormat = SimpleDateFormat(outputFormatString, Locale.getDefault())

    val date: Date? = inputFormat.parse(dateString)

    return if (date != null) outputFormat.format(date) else dateString
}

fun getGreetingMessage(hour: Int): String {
    return when {
        hour in 0..11 -> "Good morning"
        hour in 12..17 -> "Good afternoon"
        else -> "Good evening"
    }
}