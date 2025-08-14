package com.yannk.respira.data.local.model.domain

import com.yannk.respira.data.local.model.DailyReportEntity
import com.yannk.respira.data.remote.model.response.DailyReportsResponse
import com.yannk.respira.ui.components.ChartData
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import java.time.LocalDate

data class DailyReport(
    val date: LocalDate,
    val totalSessions: Int,
    val totalCough: Int,
    val totalSneeze: Int,
    val totalOtherEvents: Int,
    val predominantEnvironment: String,
    val totalDurationMinutes: Int
) {
    fun toChartData(): List<ChartData> = listOf(
        ChartData("Tosse", totalCough, CoughingColor),
        ChartData("Espirro", totalSneeze, SneezingColor),
        ChartData("Outros", totalOtherEvents, OtherColor)
    )
}

// Extensions para conversão
fun DailyReportsResponse.toEntity(): DailyReportEntity = DailyReportEntity(
    date = data,
    totalSessions = total_sessoes,
    totalCough = total_tosse,
    totalSneeze = total_espirro,
    totalOtherEvents = total_outros_eventos,
    predominantEnvironment = ambiente_predominante,
    totalDurationMinutes = duracao_total_minutos
)

fun DailyReportEntity.toDomain(): DailyReport = DailyReport(
    date = LocalDate.parse(date),
    totalSessions = totalSessions,
    totalCough = totalCough,
    totalSneeze = totalSneeze,
    totalOtherEvents = totalOtherEvents,
    predominantEnvironment = predominantEnvironment,
    totalDurationMinutes = totalDurationMinutes
)
