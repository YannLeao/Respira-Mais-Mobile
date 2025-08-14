package com.yannk.respira.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_reports")
data class DailyReportEntity(
    @PrimaryKey val date: String, // YYYY-MM-DD
    val totalSessions: Int,
    val totalCough: Int,
    val totalSneeze: Int,
    val totalOtherEvents: Int,
    val predominantEnvironment: String,
    val totalDurationMinutes: Int
)
