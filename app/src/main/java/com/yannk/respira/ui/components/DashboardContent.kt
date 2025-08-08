package com.yannk.respira.ui.components

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.yannk.respira.ui.viewmodel.DashboardViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardContent(
    viewModel: DashboardViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val vmPermissionState = viewModel.permissionState.collectAsState()
    val isMonitoring = viewModel.monitoringState.collectAsState().value
    val sessionData = viewModel.sessionData.collectAsState().value

    // Efeito para lidar com permissões
    LaunchedEffect(permissionState.status, vmPermissionState.value) {
        when (vmPermissionState.value) {
            DashboardViewModel.PermissionState.Requested -> {
                if (!permissionState.status.isGranted) {
                    permissionState.launchPermissionRequest()
                }
            }
            else -> Unit
        }
    }

    // Efeito para tratar o resultado da permissão
    LaunchedEffect(permissionState.status) {
        viewModel.onPermissionResult(
            granted = permissionState.status.isGranted,
            shouldShowRationale = permissionState.status.shouldShowRationale
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        SessionHeader(sessionData)

        DonutChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            data = viewModel.audioStats
        )

        MonitoringSwitch(
            isEnabled = isMonitoring,
            onToggle = { viewModel.toggleMonitoring() }
        )

        SessionMetrics(
            duration = sessionData.duration,
            quality = sessionData.quality
        )

        when (vmPermissionState.value) {
            DashboardViewModel.PermissionState.ShowRationale -> {
                RationaleDialog(
                    onConfirm = { permissionState.launchPermissionRequest() },
                    onDismiss = { viewModel.toggleMonitoring() }
                )
            }
            DashboardViewModel.PermissionState.Denied -> {
                PermissionWarning(
                    onRequestAgain = {
                        viewModel.toggleMonitoring()
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