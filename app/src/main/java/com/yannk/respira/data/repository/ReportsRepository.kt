package com.yannk.respira.data.repository

import com.yannk.respira.data.local.dao.ReportsDao
import com.yannk.respira.data.local.model.domain.DailyReport
import com.yannk.respira.data.local.model.domain.toDomain
import com.yannk.respira.data.local.model.domain.toEntity
import com.yannk.respira.data.remote.client.ApiClient
import java.time.LocalDate
import javax.inject.Inject

class ReportsRepository @Inject constructor(
    private val api: ApiClient,
    private val dao: ReportsDao
) {
    suspend fun getDailyReport(token: String, date: String): DailyReport {
        return try {
            // Tenta buscar da API primeiro
            val remoteReport = api.reportService.getDailySummary("Bearer $token", date).body()?.toEntity()
                ?: throw Exception("Dados inválidos")

            // Salva localmente
            dao.insertDailyReport(remoteReport)

            remoteReport.toDomain()
        } catch (e: Exception) {
            // Fallback para cache local
            dao.getDailyReport(date)?.toDomain() ?: throw e
        }
    }

    suspend fun getReportsInRange(token: String, startDate: String, endDate: String): List<DailyReport> {
        return try {
            // Tenta buscar da API primeiro
            val remoteReports = api.reportService.getRangeReports("Bearer $token", startDate, endDate).body()?.map { it.toEntity() } ?: emptyList()

            // Salva localmente
            remoteReports.forEach { dao.insertDailyReport(it) }

            remoteReports.map { it.toDomain() }
        } catch (e: Exception) {
            // Fallback para cache local
            dao.getReportsInRange(startDate, endDate).map { it.toDomain() }
        }
    }

    suspend fun getWeeklyReports(endDate: String): List<DailyReport> {
        val startDate = LocalDate.parse(endDate).minusDays(6).toString()
        return dao.getReportsInRange(startDate, endDate).map { it.toDomain() }
    }
}