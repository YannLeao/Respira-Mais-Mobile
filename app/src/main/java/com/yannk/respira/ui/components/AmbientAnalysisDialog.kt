package com.yannk.respira.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor
import kotlinx.coroutines.delay

@Composable
fun AmbientAnalysisDialog(
    totalSeconds: Int = 10,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    var secondsLeft by remember { mutableIntStateOf(totalSeconds) }

    LaunchedEffect(key1 = true) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
        onFinish()
    }

    Dialog(
        onDismissRequest = { /* Não permitir fechar */ },
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = modifier
                .width(280.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.HourglassTop,
                contentDescription = "Analisando",
                tint = ButtonColor,
                modifier = Modifier.size(48.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Analisando Ambiente",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(24.dp))

            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = ButtonColor,
                strokeWidth = 4.dp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Aguarde enquanto analisamos\no ambiente sonoro...",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "$secondsLeft segundos restantes",
                style = MaterialTheme.typography.bodySmall,
                color = TextColor
            )
        }
    }
}