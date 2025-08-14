package com.yannk.respira.service.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun String.formatSessionDateTime(): String {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(this, formatter)

        val dayPeriod = when (dateTime.hour) {
            in 6..11 -> "manhã"
            in 12..17 -> "tarde"
            in 18..23 -> "noite"
            else -> "madrugada"
        }

        val dayFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)

        "Na $dayPeriod de ${dateTime.format(dayFormatter)}"
    } catch (e: Exception) {
        "Nenhuma sessão iniciada recentemente"
    }
}

fun String.formatTime(): String {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(this, formatter)
        DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
    } catch (e: Exception) {
        "--:--"
    }
}