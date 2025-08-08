package com.yannk.respira.data.local.model

import androidx.compose.ui.graphics.Color

data class SessionData(
    val id: String,
    val dateTime: String,
    val duration: String,
    val quality: SleepQuality,
    val isActive: Boolean
) {
    companion object {
        fun empty() = SessionData(
            id = "",
            dateTime = "",
            duration = "0h 00min",
            quality = SleepQuality.UNKNOWN,
            isActive = false
        )
    }
}

enum class SleepQuality(val label: String, val color: Color) {
    GOOD("Boa", Color(0xFF4CAF50)),
    MODERATE("Moderada", Color(0xFFFFC107)),
    POOR("Ruim", Color(0xFFF44336)),
    UNKNOWN("Não monitorado", Color.Gray)
}