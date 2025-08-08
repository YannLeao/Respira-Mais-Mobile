package com.yannk.respira.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.remote.api.ApiClient
import com.yannk.respira.service.SleepMonitoringService
import com.yannk.respira.service.utils.gravarWav
import com.yannk.respira.ui.components.AudioStat
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    apiClient: ApiClient
) : ViewModel() {

    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.Idle)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _monitoringState = MutableStateFlow(false)
    val monitoringState: StateFlow<Boolean> = _monitoringState

    private val _showAmbientAnalysis = MutableStateFlow(false)
    val showAmbientAnalysis: StateFlow<Boolean> = _showAmbientAnalysis

    private val _showMonitoringStarted = MutableStateFlow(false)
    val showMonitoringStarted: StateFlow<Boolean> = _showMonitoringStarted


    // Dados do gráfico (mockados inicialmente)
    val audioStats = listOf(
        AudioStat("Tosse", 45f, CoughingColor),
        AudioStat("Espirro", 35f, SneezingColor),
        AudioStat("Outros", 20f, OtherColor)
    )

    fun onPermissionResult(granted: Boolean, shouldShowRationale: Boolean) {
        _permissionState.value = when {
            granted -> PermissionState.Granted
            shouldShowRationale -> PermissionState.ShowRationale
            else -> PermissionState.Denied
        }
    }

    fun toggleMonitoring(sessionViewModel: SessionViewModel, context: Context) {
        if (_permissionState.value != PermissionState.Granted) {
            _permissionState.value = PermissionState.Requested
            return
        }

        val newState = !_monitoringState.value
        _monitoringState.value = newState

        if (newState) {
            _showAmbientAnalysis.value = true
            sessionViewModel.iniciarSessao { sessionId ->
                analyzeEnvironment(context, sessionId, sessionViewModel) { success ->
                    _showAmbientAnalysis.value = false
                    if (success) {
                        startMonitoringService(context, sessionId)
                        _showMonitoringStarted.value = true
                    } else {
                        _monitoringState.value = false
                    }
                }
            }
        } else {
            stopMonitoringService(context)
            sessionViewModel.finalizarSessao()
        }
    }

    fun dismissMonitoringStartedDialog() {
        _showMonitoringStarted.value = false
    }

    private fun analyzeEnvironment(
        context: Context,
        sessionId: Int,
        sessionViewModel: SessionViewModel,
        callback: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            // 1. Gravação do arquivo com verificações
            val file = try {
                withContext(Dispatchers.IO) {
                    gravarWav(context).apply {
                        // Verificações robustas
                        if (!exists() || length() == 0L) {
                            throw IOException("Arquivo de áudio inválido")
                        }
                        if (!canRead()) {
                            throw SecurityException("Sem permissão de leitura")
                        }

                        // Delay adicional de segurança
                        delay(300)

                        Log.d("AudioRec", "Arquivo gravado: ${length()} bytes")
                    }
                }
            } catch (e: Exception) {
                Log.e("AudioRec", "Falha na gravação", e)
                callback(false)
                return@launch
            }

            // 2. Envio para a API
            try {
                sessionViewModel.analisarAmbiente(file) { result ->
                    val success = when {
                        result.contains("ok", ignoreCase = true) -> true
                        result.contains("erro", ignoreCase = true) -> {
                            Log.w("AudioUpload", "Erro na API: $result")
                            false
                        }
                        else -> {
                            Log.w("AudioUpload", "Resposta inesperada: $result")
                            false
                        }
                    }

                    // Limpeza e callback
                    try {
                        file.delete()
                    } catch (e: Exception) {
                        Log.w("AudioCleanup", "Falha ao deletar arquivo", e)
                    }

                    callback(success)
                }
            } catch (e: Exception) {
                Log.e("AudioUpload", "Falha no envio", e)
                try {
                    file.delete()
                } catch (e: Exception) {
                    Log.w("AudioCleanup", "Falha ao deletar arquivo", e)
                }
                callback(false)
            }
        }
    }
    private fun startMonitoringService(context: Context, sessionId: Int) {
        val intent = Intent(context, SleepMonitoringService::class.java).apply {
            putExtra("SESSION_ID", sessionId)
        }
        ContextCompat.startForegroundService(context, intent)
    }

    private fun stopMonitoringService(context: Context) {
        val intent = Intent(context, SleepMonitoringService::class.java)
        context.stopService(intent)
    }

    sealed class PermissionState {
        object Idle : PermissionState()
        object Requested : PermissionState()
        object Granted : PermissionState()
        object Denied : PermissionState()
        object ShowRationale : PermissionState()
    }
}