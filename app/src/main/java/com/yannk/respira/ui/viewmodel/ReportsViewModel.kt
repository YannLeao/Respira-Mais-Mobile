package com.yannk.respira.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.local.model.domain.DailyReport
import com.yannk.respira.data.local.model.domain.WeeklyReport
import com.yannk.respira.data.repository.ReportsRepository
import com.yannk.respira.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repository: ReportsRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _dailyReport = MutableStateFlow<DailyReportState>(DailyReportState.Loading)
    val dailyReport: StateFlow<DailyReportState> = _dailyReport

    private val _availableDates = MutableStateFlow<List<LocalDate>>(emptyList())
    val availableDates: StateFlow<List<LocalDate>> = _availableDates

    private val _weeklyReport = MutableStateFlow<WeeklyReportState>(WeeklyReportState.Loading)
    val weeklyReport: StateFlow<WeeklyReportState> = _weeklyReport

    init {
        loadAvailableDates()
    }

    fun getAvailableDates(): List<LocalDate> {
        return availableDates.value
    }

    private fun loadAvailableDates() {
        viewModelScope.launch {
            try {
                val token = userRepository.getToken() ?: return@launch
                val endDate = LocalDate.now()
                val startDate = endDate.withDayOfMonth(1)
                val reports = repository.getReportsInRange(token, startDate.toString(), endDate.toString())
                _availableDates.value = reports.map { it.date }
            } catch (e: Exception) {
                _availableDates.value = emptyList()
            }
        }
    }

    fun loadDailyReport(date: String) {
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            _dailyReport.value = DailyReportState.Loading
            try {
                val report = repository.getDailyReport(token, date)
                _dailyReport.value = DailyReportState.Success(report)
            } catch (e: Exception) {
                _dailyReport.value = DailyReportState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun loadWeeklyReport() {
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            _weeklyReport.value = WeeklyReportState.Loading
            try {
                val endDate = LocalDate.now()
                val startDate = endDate.minusDays(6)
                val dailyReports = repository.getReportsInRange(
                    token,
                    startDate.toString(),
                    endDate.toString()
                )

                // Agrega os dados para o resumo semanal
                val weeklyReport = WeeklyReport(
                    dailyReports = dailyReports,
                    totalSessions = dailyReports.sumOf { it.totalSessions },
                    totalCough = dailyReports.sumOf { it.totalCough },
                    totalSneeze = dailyReports.sumOf { it.totalSneeze },
                    totalOtherEvents = dailyReports.sumOf { it.totalOtherEvents },
                    totalDurationMinutes = dailyReports.sumOf { it.totalDurationMinutes },
                    predominantEnvironment = getPredominantEnvironment(dailyReports)
                )

                _weeklyReport.value = WeeklyReportState.Success(weeklyReport)
            } catch (e: Exception) {
                _weeklyReport.value = WeeklyReportState.Error(e.message ?: "Erro ao carregar relatório semanal")
            }
        }
    }

    private fun getPredominantEnvironment(reports: List<DailyReport>): String {
        // Filtra apenas relatórios com ambiente válido e que tiveram sessões
        val validReports = reports.filter {
            it.predominantEnvironment.isNotBlank() &&
                    it.predominantEnvironment.lowercase() != "nenhum" &&
                    it.totalSessions > 0
        }

        if (validReports.isEmpty()) {
            return "Nenhum"
        }

        // Contagem de ambientes, considerando apenas os válidos
        val counts = validReports
            .groupingBy { it.predominantEnvironment }
            .eachCount()

        // Ordem de desempate (prioriza ambientes mais silenciosos)
        val priorityOrder = listOf("Ruidoso", "Moderado", "Silencioso")

        val maxCount = counts.maxOfOrNull { it.value } ?: 0
        val candidates = counts.filter { it.value == maxCount }.keys

        // Tenta desempatar pela ordem de prioridade
        priorityOrder.forEach { env ->
            if (candidates.contains(env)) {
                return env
            }
        }

        // Se não encontrar na ordem prioritária, retorna o primeiro candidato
        return candidates.firstOrNull() ?: "Nenhum"
    }

    fun logout() {
        viewModelScope.launch { repository.logout() }
    }

    sealed class DailyReportState {
        object Loading : DailyReportState()
        data class Success(val report: DailyReport) : DailyReportState()
        data class Error(val message: String) : DailyReportState()
    }

    sealed class WeeklyReportState {
        object Loading : WeeklyReportState()
        data class Success(val report: WeeklyReport) : WeeklyReportState()
        data class Error(val message: String) : WeeklyReportState()
    }
}

