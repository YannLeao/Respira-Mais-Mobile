package com.yannk.respira.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.yannk.respira.ui.viewmodel.InicioViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun InicioScreen(modifier: Modifier, viewModel: InicioViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val permissionsState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    var isRecording = remember { mutableStateOf(false) }
    var mensagem = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (permissionsState.status.isGranted) {
            Button(onClick = {
                if (!isRecording.value) {
                    viewModel.startRecording(context)
                    isRecording.value = true
                    mensagem.value = "Gravando..."
                } else {
                    viewModel.stopRecording()
                    isRecording.value = false
                    mensagem.value = "Gravação Concluída!"
                }
            }) {
                Text(if (isRecording.value) "Parar Gravação" else "Iniciar Gravação")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                viewModel.enviarAudio {
                    mensagem.value = if (it) "Enviado com sucesso!" else "Erro no envio"
                }
            }) {
                Text("Enviar Áudio")
            }
        } else {
            Button(onClick = { permissionsState.launchPermissionRequest() }) {
                Text("Permitir Gravação de Áudio")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = mensagem.value)
    }
}