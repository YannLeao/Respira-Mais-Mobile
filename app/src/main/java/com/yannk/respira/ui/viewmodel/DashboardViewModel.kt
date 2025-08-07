package com.yannk.respira.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.yannk.respira.data.local.model.SessionData
import com.yannk.respira.data.repository.SleepRepository
import com.yannk.respira.service.SleepMonitoringService
import com.yannk.respira.ui.components.AudioStat
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val application: Application,
    private val sleepRepository: SleepRepository
) : ViewModel() {

    // Estado da permissão
    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.Idle)
    val permissionState: StateFlow<PermissionState> = _permissionState

    // Estado do monitoramento
    private val _monitoringState = MutableStateFlow(false)
    val monitoringState: StateFlow<Boolean> = _monitoringState

    // Dados da sessão
    private val _sessionData = MutableStateFlow(SessionData.empty())
    val sessionData: StateFlow<SessionData> = _sessionData

    // Dados do gráfico (mockados inicialmente)
    val audioStats = listOf(
        AudioStat("Tosse", 45f, CoughingColor),
        AudioStat("Espirro", 35f, SneezingColor),
        AudioStat("Outros", 20f, OtherColor)
    )

//    init {
//        loadInitialData()
//    }

//    private fun loadInitialData() {
//        viewModelScope.launch {
//            _sessionData.value = sleepRepository.getLatestSession()
//        }
//    }

    fun toggleMonitoring() {
        if (_permissionState.value != PermissionState.Granted) {
            _permissionState.value = PermissionState.Requested
            return
        }

        _monitoringState.value = !_monitoringState.value
        if (_monitoringState.value) {
            startMonitoringService()
        } else {
            stopMonitoringService()
        }
    }

    fun onPermissionResult(granted: Boolean) {
        _permissionState.value = if (granted) {
            PermissionState.Granted
        } else {
            PermissionState.Denied
        }

        if (granted && _monitoringState.value) {
            startMonitoringService()
        }
    }

//    fun refreshData() {
//        viewModelScope.launch {
//            _sessionData.value = sleepRepository.getLatestSession()
//        }
//    }

    private fun startMonitoringService() {
        val intent = Intent(application, SleepMonitoringService::class.java).apply {
            putExtra("SESSION_ID", _sessionData.value.id)
        }
        ContextCompat.startForegroundService(application, intent)
    }

    private fun stopMonitoringService() {
        val intent = Intent(application, SleepMonitoringService::class.java)
        application.stopService(intent)
    }

    sealed class PermissionState {
        object Idle : PermissionState()
        object Requested : PermissionState()
        object Granted : PermissionState()
        object Denied : PermissionState()
    }
}