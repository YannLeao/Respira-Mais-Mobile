package com.yannk.respira.ui.screens

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.yannk.respira.ui.components.DonutChart
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.audioStats
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DashboardHomeScreen(navController: NavHostController) {
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
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Monitoramento | Respira+",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextColor
        )

        // Gráfico Donut
        DonutChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            data = audioStats
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ativar Monitoramento Noturno",
                    modifier = Modifier.padding(end = 8.dp),
                    color = ButtonColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )

                Switch(
                    checked = isServiceRunning.value,
                    onCheckedChange = { checked ->
                        isServiceRunning.value = checked
                        toggleService(checked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = ButtonColor,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }
        }

        // Botão para microfone
        Button(
            onClick = { navController.navigate(Routes.MICROPHONE) },
            colors = ButtonDefaults.buttonColors(containerColor = ButtonColor),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(
                text = "Ir para microfone",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
private fun DashBoard() {
    DashboardHomeScreen(navController = rememberNavController())
}
