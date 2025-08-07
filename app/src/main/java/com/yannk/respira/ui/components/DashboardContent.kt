package com.yannk.respira.ui.components

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yannk.respira.service.SleepMonitoringService
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardContent(
    navController: NavHostController,
    modifier: Modifier
) {
    val context = LocalContext.current
    val permissionsState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val isServiceRunning = remember { mutableStateOf(false) }

    fun toggleService(enable: Boolean) {
        val intent = Intent(context, SleepMonitoringService::class.java)
        if (enable) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.stopService(intent)
        }
    }

    Column(
        modifier = Modifier.Companion
            .fillMaxSize()
            .background(Color.Companion.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Companion.CenterVertically)
    ) {
        Text(
            text = "Monitoramento | Respira+",
            fontSize = 24.sp,
            fontWeight = FontWeight.Companion.Bold,
            color = TextColor
        )

        // Gráfico Donut
        DonutChart(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .padding(16.dp),
            data = audioStats
        )

        Spacer(modifier = Modifier.Companion.height(16.dp))

        // Switch com label
        if (permissionsState.status.isGranted) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        isServiceRunning.value = !isServiceRunning.value
                        toggleService(isServiceRunning.value)
                    }
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = "Ativar Monitoramento Noturno",
                    modifier = Modifier.padding(end = 8.dp),
                    color = ButtonColor,
                    fontWeight = FontWeight.Companion.Medium,
                    fontSize = 16.sp
                )

                Switch(
                    checked = isServiceRunning.value,
                    onCheckedChange = { checked ->
                        isServiceRunning.value = checked
                        toggleService(checked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Companion.White,
                        uncheckedThumbColor = Color.Companion.White,
                        checkedTrackColor = ButtonColor,
                        uncheckedTrackColor = Color.Companion.Gray
                    )
                )
            }
        }

        Button(
            onClick = { navController.navigate(Routes.MICROPHONE) },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Ir para microfone",
                color = Color.Companion.White,
                fontWeight = FontWeight.Companion.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun DashBoardContentPrev() {
    DashboardContent(navController = rememberNavController(), modifier = Modifier)
}