package com.yannk.respira.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.local.model.SessionData
import com.yannk.respira.data.local.model.SessionEntity
import com.yannk.respira.data.local.model.SleepEnvironment
import com.yannk.respira.data.local.model.SleepQuality
import com.yannk.respira.data.repository.SessionRepository
import com.yannk.respira.data.repository.UserRepository
import com.yannk.respira.service.SleepMonitoringService
import com.yannk.respira.service.utils.formatTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _latestSession = MutableStateFlow(SessionData.empty())
    val latestSession: StateFlow<SessionData> = _latestSession

    private var currentSessionId: Int? = null

    fun iniciarSessao(callback: (Int) -> Unit) {
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            val sessionId = sessionRepository.iniciarSessao(token)
            currentSessionId = sessionId
            callback(sessionId)
        }
    }

    fun analisarAmbiente(file: File, onResult: (String) -> Unit) {
        val sessionId = currentSessionId ?: return
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            val result = sessionRepository.analisarAmbiente(token, sessionId, file)
            onResult(result)
        }
    }

    fun startMonitoringService(context: Context, sessionId: Int) {
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            val intent = Intent(context, SleepMonitoringService::class.java).apply {
                putExtra("token", token)
                putExtra("session_id", sessionId)
            }
            ContextCompat.startForegroundService(context, intent)
        }
    }

    fun stopMonitoringService(context: Context) {
        val intent = Intent(context, SleepMonitoringService::class.java)
        context.stopService(intent)
    }

    fun finalizarSessao() {
        val sessionId = currentSessionId ?: return
        viewModelScope.launch {
            val token = userRepository.getToken() ?: return@launch
            val report = sessionRepository.finalizarSessao(token, sessionId)
            sessionRepository.salvarSessao(report)
            loadLatestSession()
            currentSessionId = null
        }
    }

    fun getSessionId(): Int? = currentSessionId

    fun loadLatestSession() {
        viewModelScope.launch {
            sessionRepository.getLatestSession()?.let {
                _latestSession.value = it.toSessionData()
            }
        }
    }

    fun logout() {
        viewModelScope.launch { sessionRepository.logout() }
    }

    private fun SessionEntity.toSessionData(): SessionData {
        return SessionData(
            id = sessionId.toString(),
            dateTime = dataHoraInicio,
            startTime = dataHoraInicio.formatTime(),
            endTime = dataHoraFim.formatTime(),
            duration = calculateDuration(dataHoraInicio, dataHoraFim),
            quality = estimateSleepQuality(quantidadeTosse, quantidadeEspirro, outrosEventos),
            environment = when(ambiente.lowercase()) {
                "silencioso" -> SleepEnvironment.SILENT
                "moderado" -> SleepEnvironment.MODERATE
                "ruidoso" -> SleepEnvironment.NOISY
                else -> SleepEnvironment.UNKNOWN
            },
            isActive = false,
            coughCount = quantidadeTosse,
            sneezeCount = quantidadeEspirro,
            otherEvents = outrosEventos
        )
    }

    private fun estimateSleepQuality(tosse: Int, espirro: Int, outros: Int): SleepQuality {
        val total = tosse + espirro + outros

        return when {
            total < 10 -> SleepQuality.GOOD
            total < 25 -> SleepQuality.MODERATE
            else -> SleepQuality.POOR
        }
    }

    private fun calculateDuration(start: String, end: String): String {
        return try {
            val startTime = LocalDateTime.parse(start)
            val endTime = LocalDateTime.parse(end)

            val duration = Duration.between(startTime, endTime)
            val hours = duration.toHours()
            val minutes = duration.toMinutes()

            "${hours}h ${minutes}min"
        } catch (e: Exception) {
            "0h 00min"
        }
    }
}
