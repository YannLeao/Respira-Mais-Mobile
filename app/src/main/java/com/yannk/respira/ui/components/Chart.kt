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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import com.yannk.respira.ui.theme.SnoringColor

/**
 * Exibe um gráfico de pizza (Donut) com uma legenda customizável.
 *
 * @param data A lista de dados `AudioStat` para desenhar no gráfico.
 * @param modifier O Modifier a ser aplicado ao container principal.
 * @param chartSize O diâmetro do gráfico. O valor padrão é 200.dp.
 * @param legendOnSide Se `true`, a legenda será posicionada ao lado do gráfico (layout em Row).
 * Se `false` (padrão), a legenda ficará abaixo do gráfico (layout em Column).
 */
@Composable
fun DonutChart(
    data: List<AudioStat>,
    modifier: Modifier = Modifier,
    chartSize: Dp = 200.dp,
    legendOnSide: Boolean = false
) {
    // Componente interno que desenha apenas o gráfico no Canvas
    val chart = @Composable {
        Box(
            modifier = Modifier.size(chartSize),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val total = data.sumOf { it.percentage.toDouble() }.toFloat()
                var startAngle = -90f
                val radius = size.minDimension / 2.5f
                val center = Offset(size.width / 2, size.height / 2)
                val strokeWidth = radius / 2.5f

                // Desenha o fundo cinza do gráfico
                drawArc(
                    color = Color.LightGray.copy(alpha = 0.3f),
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2),
                    style = Stroke(width = strokeWidth)
                )

                // Desenha cada fatia do gráfico
                data.forEach { stat ->
                    val sweepAngle = (stat.percentage / total) * 360f
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
    }

    // Componente interno que desenha apenas a legenda
    val legend = @Composable {
        Legend(data = data)
    }

    // A lógica principal que decide o layout
    if (legendOnSide) {
        // Layout com legenda ao lado
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
        // Layout padrão com legenda abaixo
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

/**
 * Um Composable privado que renderiza a lista de itens da legenda.
 */
@Composable
private fun Legend(data: List<AudioStat>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        data.forEach { stat ->
            LegendItem(stat = stat)
        }
    }
}

/**
 * Um Composable privado que renderiza um único item da legenda (cor + texto).
 */
@Composable
private fun LegendItem(stat: AudioStat) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(stat.color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "${stat.label}: ${stat.percentage.toInt()}%",
            fontSize = 14.sp
        )
    }
}


data class AudioStat(val label: String, val percentage: Float, val color: Color)
val audioStats = listOf(
    AudioStat("Espirro", 25f, SneezingColor),
    AudioStat("Tosse", 35f, CoughingColor),
    AudioStat("Ronco", 30f, SnoringColor),
    AudioStat("Outros", 10f, OtherColor)
)