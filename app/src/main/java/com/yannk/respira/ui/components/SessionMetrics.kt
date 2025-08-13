package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yannk.respira.data.local.model.SleepEnvironment
import com.yannk.respira.data.local.model.SleepQuality

@Composable
fun SessionMetrics(
    duration: String,
    quality: SleepQuality,
    startTime: String,
    endTime: String,
    environment: SleepEnvironment
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card único para Tempo de Sessão
        SessionTimeCard(
            duration = duration,
            startTime = startTime,
            endTime = endTime
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            // Card de Qualidade
            MetricCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Assessment,
                title = "Qualidade",
                value = quality.label,
                color = when(quality) {
                    SleepQuality.GOOD -> SleepQuality.GOOD.color
                    SleepQuality.MODERATE -> SleepQuality.MODERATE.color
                    SleepQuality.POOR -> SleepQuality.POOR.color
                    SleepQuality.UNKNOWN -> SleepQuality.UNKNOWN.color
                }
            )

            // Card de Ambiente
            MetricCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.Place,
                title = "Ambiente",
                value = environment.label,
                color = when(environment) {
                    SleepEnvironment.SILENT -> SleepEnvironment.SILENT.color
                    SleepEnvironment.MODERATE -> SleepEnvironment.MODERATE.color
                    SleepEnvironment.NOISY -> SleepEnvironment.NOISY.color
                    SleepEnvironment.UNKNOWN -> SleepEnvironment.UNKNOWN.color
                }
            )
        }
    }
}

@Composable
private fun SessionTimeCard(
    duration: String,
    startTime: String,
    endTime: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = "Ícone de tempo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "Tempo de Sessão",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Divisor
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )

            // Time Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TimeInfoItem(title = "Início", value = startTime)
                TimeInfoItem(title = "Término", value = endTime)
                TimeInfoItem(title = "Duração", value = duration)
            }
        }
    }
}

@Composable
private fun TimeInfoItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun MetricCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}