package com.yannk.respira.data.local.model

import androidx.compose.ui.graphics.Color

data class SessionData(
    val id: String,
    val dateTime: String,
    val startTime: String,
    val endTime: String,
    val duration: String,
    val quality: SleepQuality,
    val environment: SleepEnvironment,
    val isActive: Boolean,
    val coughCount: Int,
    val sneezeCount: Int,
    val otherEvents: Int
) {
    companion object {
        fun empty() = SessionData(
            id = "",
            dateTime = "",
            startTime = "",
            endTime = "",
            duration = "0h 00min",
            quality = SleepQuality.UNKNOWN,
            environment = SleepEnvironment.UNKNOWN,
            isActive = false,
            coughCount = 0,
            sneezeCount = 0,
            otherEvents = 0
        )
    }
}

enum class SleepQuality(val label: String, val color: Color) {
    GOOD("Boa", Color(0xFF4CAF50)),
    MODERATE("Moderada", Color(0xFFFFC107)),
    POOR("Ruim", Color(0xFFF44336)),
    UNKNOWN("Não monitorado", Color.Gray)
}

enum class SleepEnvironment(val label: String, val color: Color) {
    SILENT("Silencioso", Color(0xFF4CAF50)),
    MODERATE("Moderado", Color(0xFFFFC107)),
    NOISY("Ruidoso", Color(0xFFF44336)),
    UNKNOWN("Desconhecido", Color.Gray)
}