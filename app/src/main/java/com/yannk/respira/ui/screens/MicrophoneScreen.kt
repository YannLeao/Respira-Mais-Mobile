package com.yannk.respira.ui.screens

import android.Manifest
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yannk.respira.service.SleepMonitoringService
import com.yannk.respira.ui.viewmodel.MicrophoneViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MicrophoneScreen(
    navController: NavHostController,
    viewModel: MicrophoneViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionsState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    val isRecording = remember { mutableStateOf(false) }
    val isRecorded = remember { mutableStateOf(false) }
    val countdown = remember { mutableIntStateOf(5) }
    val message = remember { mutableStateOf("") }

    val isServiceRunning = remember { mutableStateOf(false) }

    fun toggleService(enable: Boolean) {
        val intent = Intent(context, SleepMonitoringService::class.java)
        if (enable) {
            ContextCompat.startForegroundService(context, intent)
        } else {
            context.stopService(intent)
        }
    }

    LaunchedEffect(isRecording.value) {
        if (isRecording.value) {
            countdown.intValue = 5
            for (i in 4 downTo 0) {
                delay(1000)
                countdown.intValue = i
            }

            viewModel.startRecording(context)
            delay(5000)

            isRecording.value = false
            isRecorded.value = true
            message.value = "Gravação concluída!"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (permissionsState.status.isGranted) {

            Row(
                modifier = Modifier
                    .clickable {
                        isServiceRunning.value = !isServiceRunning.value
                        toggleService(isServiceRunning.value)
                    }
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Monitoramento Noturno",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.width(30.dp))

                Switch(
                    checked = isServiceRunning.value,
                    onCheckedChange = { checked ->
                        isServiceRunning.value = checked
                        toggleService(checked)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                        uncheckedTrackColor = Color.Gray
                    )
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            Icon(
                imageVector = if (isRecording.value) Icons.Filled.Mic else Icons.Filled.MicOff,
                tint = if (isRecording.value) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier.size(100.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(40.dp))

            if (isRecording.value) {
                Text("Gravando em ${countdown.intValue}s...")
            } else {
                Text(
                    text = if (isRecorded.value) "Gravação pronta para envio" else "Pronto para gravar",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!isRecording.value && !isRecorded.value) {
                Button(
                    onClick = {
                        isRecording.value = true
                        message.value = "Gravando..."
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Iniciar Gravação")
                }
            }

            if (isRecorded.value) {
                Button(
                    onClick = {
                        viewModel.enviarAudio {
                            isRecorded.value = false
                            message.value = if (it) "Enviado com sucesso!" else "Erro no envio"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Enviar Áudio")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(message.value)
        } else {
            Button(
                onClick = { permissionsState.launchPermissionRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text("Permitir Gravação de Áudio")
            }
        }
    }
}
