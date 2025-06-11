package com.yannk.respira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.theme.RespiraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RespiraTheme {
                var showWelcome by remember { mutableStateOf(true) }
                var isRecording by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (showWelcome) {
                        WelcomeScreen(
                            onStartClick = { showWelcome = false }
                        )
                    } else {
                        SleepMonitorScreen(
                            isRecording = isRecording,
                            onStartRecording = { isRecording = true },
                            onStopRecording = { isRecording = false },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun SleepMonitorScreen(
    isRecording: Boolean,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isRecording) Icons.Filled.Mic else Icons.Filled.MicOff,
            contentDescription = null,
            tint = if (isRecording) Color.Red else Color.Gray,
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (isRecording) "Gravando..." else "Pronto para gravar",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (isRecording) onStopRecording() else onStartRecording()
        }) {
            Text(text = if (isRecording) "Parar" else "Iniciar")
        }
    }
}

@Composable
fun WelcomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4598A0))
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(

            painter = painterResource(id = R.drawable.logo_respiratory), // coloque o nome do arquivo aqui
            contentDescription = "Logo Respiratory Monitoring",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Respiratory Monitoring",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onStartClick, modifier = Modifier.width(200.dp).height(50.dp)) {

            Text(
                text = "Comece já",
                style = MaterialTheme.typography.headlineSmall
                )

        }
    }
}


@Preview(showBackground = true)
@Composable
fun SleepMonitorPreview() {
    RespiraTheme {
        SleepMonitorScreen(
            isRecording = false,
            onStartRecording = {},
            onStopRecording = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview(){
    RespiraTheme {
        WelcomeScreen {

        }
    }
}
