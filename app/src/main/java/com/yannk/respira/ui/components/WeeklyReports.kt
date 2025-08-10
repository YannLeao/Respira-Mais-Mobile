package com.yannk.respira.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor
import com.yannk.respira.ui.viewmodel.ReportsViewModel

@Composable
fun WeeklyReports(
    modifier: Modifier = Modifier,
    viewModel: ReportsViewModel = hiltViewModel()
) {

    val weeklyData = listOf(
        "Seg" to 150f,
        "Ter" to 280f,
        "Qua" to 90f,
        "Qui" to 210f,
        "Sex" to 180f,
        "Sáb" to 300f,
        "Dom" to 120f
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Header com título e período
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Relatório Semanal",
                style = MaterialTheme.typography.headlineSmall,
                color = ButtonColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "De 08 a 14 de Julho de 2024",
                style = MaterialTheme.typography.bodyMedium,
                color = TextColor
            )
        }

        // Gráfico aprimorado
        ElevatedCard(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Atividade Sonora",
                    style = MaterialTheme.typography.titleMedium,
                    color = ButtonColor,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                WeeklyChart(
                    data = weeklyData,
                    modifier = Modifier.height(220.dp)
                )
            }
        }

        // Métricas em Grid
        GridMetrics(
            totalEvents = weeklyData.sumOf { it.second.toInt() },
            avgDuration = weeklyData.map { it.second }.average().toFloat(),
            qualityScore = calculateQualityScore(weeklyData)
        )
    }
}

@Composable
private fun GridMetrics(
    totalEvents: Int,
    avgDuration: Float,
    qualityScore: Float
) {
    val metrics = listOf(
        Triple("Total de Eventos", "$totalEvents", ButtonColor),
        Triple("Duração Média", "${"%.1f".format(avgDuration)}h", AzulMedio),
        Triple("Qualidade do Sono", "${(qualityScore * 100).toInt()}%", getQualityColor(qualityScore))
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth().height(300.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(metrics.size) { index ->
            val (title, value, color) = metrics[index]
            MetricCard(title, value, color)
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextColor.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WeeklyChart(
    data: List<Pair<String, Float>>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 200.dp,
    lineColor: Color = ButtonColor,
    fillGradient: Boolean = true
) {
    if (data.isEmpty()) {
        PlaceholderChart()
        return
    }

    val maxValue = data.maxOfOrNull { it.second } ?: 1f
    val minValue = data.minOfOrNull { it.second } ?: 0f
    val range = maxValue - minValue

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight + 40.dp) // Espaço para rótulos
    ) {
        val paddingStart = 24.dp.toPx()
        val paddingEnd = 8.dp.toPx()
        val usableWidth = size.width - paddingStart - paddingEnd
        val itemWidth = usableWidth / (data.size - 1)
        val zeroLine = size.height * 0.9f // Linha base (90% da altura)

        // Converte os dados em pontos (x,y)
        val points = data.mapIndexed { index, (_, value) ->
            val x = paddingStart + (index * itemWidth)
            val y = zeroLine - ((value - minValue) / range) * (size.height * 0.8f)
            Offset(x, y)
        }

        // Preenchimento abaixo da linha (opcional)
        if (fillGradient) {
            val path = Path().apply {
                moveTo(paddingStart, zeroLine)
                points.forEach { lineTo(it.x, it.y) }
                lineTo(paddingStart + usableWidth, zeroLine)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        lineColor.copy(alpha = 0.2f),
                        lineColor.copy(alpha = 0.05f)
                    ),
                    startY = zeroLine,
                    endY = 0f
                )
            )
        }

        // Desenha a linha principal
        drawPath(
            path = Path().apply {
                points.forEachIndexed { index, point ->
                    if (index == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
            },
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Desenha os pontos de dados
        points.forEach { point ->
            drawCircle(
                color = lineColor,
                radius = 4.dp.toPx(),
                center = point
            )
        }

        // Desenha os rótulos dos dias
        data.forEachIndexed { index, (day, _) ->
            val x = paddingStart + (index * itemWidth)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    day.take(3),
                    x,
                    size.height - 8.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = TextColor.toArgb()
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

@Composable
private fun PlaceholderChart() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.LightGray.copy(alpha = 0.1f))
            .border(1.dp, ButtonColor.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Sem dados disponíveis",
            color = TextColor.copy(alpha = 0.5f)
        )
    }
}

private fun calculateQualityScore(data: List<Pair<String, Float>>): Float {
    val total = data.sumOf { it.second.toDouble() }
    val avg = total / data.size
    return when {
        avg < 100 -> 0.9f
        avg < 200 -> 0.7f
        else -> 0.5f
    }
}

private fun getQualityColor(score: Float): Color {
    return when {
        score >= 0.8f -> Color(0xFF4CAF50) // Verde
        score >= 0.5f -> Color(0xFFFFC107) // Âmbar
        else -> Color(0xFFF44336) // Vermelho
    }
}
        