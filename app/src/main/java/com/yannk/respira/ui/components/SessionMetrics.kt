package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.yannk.respira.data.local.model.SleepQuality
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.TextColor

@Composable
fun SessionMetrics(
    duration: String,
    quality: SleepQuality
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Card de Duração
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.AccessTime,
            title = "Duração",
            value = duration,
            color = AzulMedio
        )

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
        colors = CardDefaults.cardColors(containerColor = Color.White)
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
                color = TextColor
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