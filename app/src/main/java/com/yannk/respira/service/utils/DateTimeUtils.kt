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
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        "Na $dayPeriod de ${dateTime.format(dayFormatter)} às ${dateTime.format(timeFormatter)}"
    } catch (e: Exception) {
        "Sessão iniciada recentemente"
    }
}