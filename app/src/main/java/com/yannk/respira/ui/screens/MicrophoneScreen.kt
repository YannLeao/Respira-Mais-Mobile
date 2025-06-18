package com.yannk.respira.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.viewmodel.MicrophoneViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MicrophoneScreen(
    navController: NavHostController,
    viewModel: MicrophoneViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val permissionsState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    var isRecording = remember { mutableStateOf(false) }
    var message = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (permissionsState.status.isGranted) {

            Icon(
                imageVector = if (isRecording.value) Icons.Filled.Mic else Icons.Filled.MicOff,
                tint = if (isRecording.value) Color.Red else Color.Gray,
                modifier = Modifier.size(100.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = if (isRecording.value) "Gravando..." else "Pronto para gravar",
                style = MaterialTheme.typography.headlineSmall
            )

            Button(
                onClick = {
                if (!isRecording.value) {
                    viewModel.startRecording(context)
                    isRecording.value = true
                    message.value = "Gravando..."
                } else {
                    viewModel.stopRecording()
                    isRecording.value = false
                    message.value = "Gravação Concluída!"
                } },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text(if (isRecording.value) "Parar" else "Iniciar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.enviarAudio {
                    message.value = if (it) "Enviado com sucesso!" else "Erro no envio"
                } },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text("Enviar Áudio")
            }

        } else {
            Button(
                onClick = { permissionsState.launchPermissionRequest() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor,
                    contentColor = Color.White
                )
            ) {
                Text("Permitir Gravação de Áudio")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = message.value)
    }
}