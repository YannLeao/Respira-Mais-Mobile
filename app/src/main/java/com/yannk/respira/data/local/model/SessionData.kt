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
            dateTime = "2025-07-08T20:38:00.000",
            duration = "5h 50min",
            quality = SleepQuality.MODERATE,
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