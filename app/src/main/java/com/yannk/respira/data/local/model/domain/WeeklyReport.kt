package com.yannk.respira.data.local.model.domain

data class WeeklyReport(
    val dailyReports: List<DailyReport>,
    val totalSessions: Int,
    val totalCough: Int,
    val totalSneeze: Int,
    val totalOtherEvents: Int,
    val totalDurationMinutes: Int,
    val predominantEnvironment: String
)