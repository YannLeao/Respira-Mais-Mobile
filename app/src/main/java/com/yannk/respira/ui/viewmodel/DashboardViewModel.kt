package com.yannk.respira.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yannk.respira.data.repository.UserRepository
import com.yannk.respira.service.utils.gravarWav
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    private val _permissionState = MutableStateFlow<PermissionState>(PermissionState.Idle)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _monitoringState = MutableStateFlow(false)
    val monitoringState: StateFlow<Boolean> = _monitoringState

    private val _showAmbientAnalysis = MutableStateFlow(false)
    val showAmbientAnalysis: StateFlow<Boolean> = _showAmbientAnalysis

    private val _showMonitoringStarted = MutableStateFlow(false)
    val showMonitoringStarted: StateFlow<Boolean> = _showMonitoringStarted

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
                        sessionViewModel.startMonitoringService(context, sessionId)
                        _showMonitoringStarted.value = true
                    } else {
                        _monitoringState.value = false
                    }
                }
            }
        } else {
            sessionViewModel.stopMonitoringService(context)
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
                    gravarWav(context)
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

    sealed class PermissionState {
        object Idle : PermissionState()
        object Requested : PermissionState()
        object Granted : PermissionState()
        object Denied : PermissionState()
        object ShowRationale : PermissionState()
    }
}