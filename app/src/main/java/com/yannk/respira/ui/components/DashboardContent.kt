package com.yannk.respira.ui.components

import android.Manifest
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yannk.respira.data.local.model.SessionData
import com.yannk.respira.ui.viewmodel.DashboardViewModel
import com.yannk.respira.ui.viewmodel.SessionViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardContent(
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    sessionViewModel: SessionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val vmPermissionState = dashboardViewModel.permissionState.collectAsState()
    val isMonitoring = dashboardViewModel.monitoringState.collectAsState().value
    val sessionData = sessionViewModel.latestSession.collectAsState().value
    val showAmbientAnalysis = dashboardViewModel.showAmbientAnalysis.collectAsState().value
    val showMonitoringStarted = dashboardViewModel.showMonitoringStarted.collectAsState().value

    LaunchedEffect(permissionState.status) {
        dashboardViewModel.onPermissionResult(
            granted = permissionState.status.isGranted,
            shouldShowRationale = permissionState.status.shouldShowRationale
        )
    }

    LaunchedEffect(Unit) {
        sessionViewModel.loadLatestSession()
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SessionHeader(sessionData)

        if (sessionData == SessionData.empty()) {
            EmptyChart(
                modifier = modifier,
                isEmptySession = true
            )
        } else {
            DonutChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                coughCount = sessionData.coughCount,
                sneezeCount = sessionData.sneezeCount,
                otherEvents = sessionData.otherEvents
            )
        }

        MonitoringSwitch(
            isEnabled = isMonitoring,
            onToggle = { dashboardViewModel.toggleMonitoring(sessionViewModel, context) },
            isProcessing = showAmbientAnalysis
        )

        SessionMetrics(
            duration = sessionData.duration,
            quality = sessionData.quality,
            startTime = sessionData.startTime,
            endTime = sessionData.endTime,
            environment = sessionData.environment
        )

        if (showAmbientAnalysis) {
            AmbientAnalysisDialog(
                totalSeconds = 10,
                onFinish = { /* Fecha automaticamente */ }
            )
        }

        if (showMonitoringStarted) {
            MonitoringStartedDialog(
                onConfirm = { dashboardViewModel.dismissMonitoringStartedDialog() }
            )
        }

        when (vmPermissionState.value) {
            DashboardViewModel.PermissionState.ShowRationale -> {
                RationaleDialog(
                    onConfirm = { permissionState.launchPermissionRequest() },
                    onDismiss = { dashboardViewModel.toggleMonitoring(sessionViewModel, context) }
                )
            }
            DashboardViewModel.PermissionState.Denied -> {
                PermissionWarning(
                    onRequestAgain = {
                        dashboardViewModel.toggleMonitoring(sessionViewModel, context)
                    }
                )
            }
            else -> Unit
        }
    }
}

@Preview
@Composable
private fun DashBoardContentPrev() {
    DashboardContent(modifier = Modifier)
}