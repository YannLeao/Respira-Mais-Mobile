package com.yannk.respira.ui.components.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Sick
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.yannk.respira.data.local.model.domain.DailyReport
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.SneezingColor

@Composable
fun DailyReportCard(report: DailyReport) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título do dia
            Text(
                text = "Dia ${report.date.dayOfMonth}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // Gráfico e Métricas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                DonutChart(
                    coughCount = report.totalCough,
                    sneezeCount = report.totalSneeze,
                    otherEvents = report.totalOtherEvents,
                    chartSize = 150.dp,
                    modifier = Modifier.weight(1f)
                )

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MetricItem(
                        icon = Icons.Default.AccessTime,
                        title = "Duração",
                        value = "${report.totalDurationMinutes} min",
                        color = MaterialTheme.colorScheme.primary
                    )

                    MetricItem(
                        icon = Icons.Default.List,
                        title = "Sessões",
                        value = report.totalSessions.toString(),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Métricas inferiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(
                    icon = Icons.Default.Sick,
                    title = "Tosses",
                    value = report.totalCough.toString(),
                    color = CoughingColor
                )

                MetricItem(
                    icon = Icons.Default.Sick,
                    title = "Espirros",
                    value = report.totalSneeze.toString(),
                    color = SneezingColor
                )

                MetricItem(
                    icon = Icons.Default.Place,
                    title = "Ambiente",
                    value = report.predominantEnvironment.capitalize(),
                    color = when(report.predominantEnvironment.lowercase()) {
                        "silencioso" -> Color(0xFF4CAF50)
                        "moderado" -> Color(0xFFFFC107)
                        "ruidoso" -> Color(0xFFF44336)
                        else -> MaterialTheme.colorScheme.secondary
                    }
                )
            }
        }
    }
}

@Composable
private fun MetricItem(
    icon: ImageVector,
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = color,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}