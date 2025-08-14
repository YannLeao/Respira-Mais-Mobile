package com.yannk.respira.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yannk.respira.data.local.model.SessionData
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor

@Composable
fun DonutChart(
    sessionData: SessionData,
    modifier: Modifier = Modifier,
    chartSize: Dp = 200.dp,
    legendOnSide: Boolean = false
) {
    // Prepara os dados para o gráfico
    val data = listOf(
        EventStat("Tosse", sessionData.coughCount, CoughingColor),
        EventStat("Espirro", sessionData.sneezeCount, SneezingColor),
        EventStat("Outros", sessionData.otherEvents, OtherColor)
    )

    val totalEvents = data.sumOf { it.count }

    // Componente para mostrar quando não há dados
    if (totalEvents == 0) {
        EmptyChart(
            modifier = modifier,
            chartSize = chartSize,
            legendOnSide = legendOnSide,
            isEmptySession = sessionData == SessionData.empty()
        )
        return
    }

    // Componente interno que desenha apenas o gráfico no Canvas
    val chart = @Composable {
        Box(
            modifier = Modifier.size(chartSize),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                var startAngle = -90f
                val radius = size.minDimension / 2.5f
                val center = Offset(size.width / 2, size.height / 2)
                val strokeWidth = radius / 2.5f

                // Desenha o fundo cinza do gráfico
                drawArc(
                    color = Color.White.copy(alpha = 0.3f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth)
                )

                // Desenha cada fatia do gráfico
                data.forEach { stat ->
                    if (stat.count > 0) {
                        val sweepAngle = (stat.count.toFloat() / totalEvents) * 360f
                        drawArc(
                            color = stat.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            topLeft = Offset(center.x - radius, center.y - radius),
                            size = Size(radius * 2, radius * 2),
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        startAngle += sweepAngle
                    }
                }
            }

            // Mostra o total no centro
            Text(
                text = "$totalEvents",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }

    // Componente interno que desenha apenas a legenda
    val legend = @Composable {
        Legend(data = data)
    }

    // Layout do gráfico com legenda
    if (legendOnSide) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            chart()
            Spacer(modifier = Modifier.width(24.dp))
            legend()
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            chart()
            Spacer(modifier = Modifier.width(16.dp))
            legend()
        }
    }
}

@Composable
private fun EmptyChart(
    modifier: Modifier,
    chartSize: Dp,
    legendOnSide: Boolean,
    isEmptySession: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(chartSize),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.InsertChart,
                contentDescription = null,
                modifier = Modifier.size(200.dp),
                tint = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isEmptySession) {
                "Nenhuma sessão realizada"
            } else {
                "Nenhum evento registrado na última sessão"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
private fun Legend(data: List<EventStat>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        data.forEach { stat ->
            LegendItem(stat = stat)
        }
    }
}

@Composable
private fun LegendItem(stat: EventStat) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(stat.color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${stat.label}: ${stat.count}",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

data class EventStat(
    val label: String,
    val count: Int,
    val color: Color
)