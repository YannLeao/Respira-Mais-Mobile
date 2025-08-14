package com.yannk.respira.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yannk.respira.data.local.model.domain.WeeklyReport
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import com.yannk.respira.ui.viewmodel.ReportsViewModel
import com.yannk.respira.ui.viewmodel.ReportsViewModel.WeeklyReportState
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeeklyReports(
    modifier: Modifier = Modifier,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    val weeklyState by viewModel.weeklyReport.collectAsState()
    val endDate = remember { LocalDate.now() }
    val startDate = remember { endDate.minusDays(6) }

    LaunchedEffect(Unit) {
        viewModel.loadWeeklyReport()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Header
        ReportHeader(
            title = "Relatório Semanal",
            subtitle = "${startDate.dayOfMonth} a ${endDate.dayOfMonth} de " +
                    "${endDate.month.getDisplayName(TextStyle.FULL, Locale("pt", "BR"))}"
        )

        when (weeklyState) {
            is WeeklyReportState.Success -> {
                val report = (weeklyState as WeeklyReportState.Success).report

                // Gráfico de Linha
                WeeklyChartCard(report)

                // Métricas Resumo
                WeeklySummaryMetrics(report)
            }
            is WeeklyReportState.Error -> {
                ErrorMessage((weeklyState as WeeklyReportState.Error).message)
            }
            WeeklyReportState.Loading -> {
                LoadingPlaceholder()
            }
        }
    }
}

@Composable
private fun WeeklyChartCard(report: WeeklyReport) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Atividade por Dia",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Gráfico com 3 linhas (tosse, espirro, outros)
            MultiLineChart(
                coughData = report.dailyReports.map { it.totalCough.toFloat() },
                sneezeData = report.dailyReports.map { it.totalSneeze.toFloat() },
                otherData = report.dailyReports.map { it.totalOtherEvents.toFloat() },
                dayLabels = report.dailyReports.map { it.date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("pt", "BR")) }
            )
        }
    }
}

@Composable
private fun WeeklySummaryMetrics(report: WeeklyReport) {
    val totalEvents = report.totalCough + report.totalSneeze + report.totalOtherEvents

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Total de Sessões
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.List,
            title = "Sessões",
            value = report.totalSessions.toString(),
            color = MaterialTheme.colorScheme.primary
        )

        // Total de Eventos
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Event,
            title = "Eventos",
            value = totalEvents.toString(),
            color = MaterialTheme.colorScheme.primary
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Ambiente Predominante
        MetricCard(
            modifier = Modifier.weight(1f),
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

        // Duração Total
        MetricCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.AccessTime,
            title = "Duração",
            value = "${report.totalDurationMinutes} min",
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun MultiLineChart(
    coughData: List<Float>,
    sneezeData: List<Float>,
    otherData: List<Float>,
    dayLabels: List<String>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 200.dp
) {
    val allData = coughData + sneezeData + otherData
    val maxValue = allData.maxOrNull() ?: 1f
    val minValue = allData.minOrNull() ?: 0f

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight + 40.dp)
    ) {
        val paddingStart = 32.dp.toPx()
        val paddingEnd = 16.dp.toPx()
        val usableWidth = size.width - paddingStart - paddingEnd
        val itemWidth = usableWidth / (dayLabels.size - 1).coerceAtLeast(1)
        val zeroLine = size.height * 0.8f

        // Função auxiliar para converter dados em pontos
        fun dataToPoints(data: List<Float>): List<Offset> {
            return data.mapIndexed { index, value ->
                val x = paddingStart + (index * itemWidth)
                val y = zeroLine - ((value - minValue) / (maxValue - minValue).coerceAtLeast(1f)) * (size.height * 0.7f)
                Offset(x, y)
            }
        }

        // Desenha as linhas
        listOf(
            Triple(coughData, CoughingColor, "Tosse"),
            Triple(sneezeData, SneezingColor, "Espirro"),
            Triple(otherData, OtherColor, "Outros")
        ).forEach { (data, color, _) ->
            val points = dataToPoints(data)

            drawPath(
                path = Path().apply {
                    points.forEachIndexed { index, point ->
                        if (index == 0) moveTo(point.x, point.y)
                        else lineTo(point.x, point.y)
                    }
                },
                color = color,
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Pontos nos dados
            points.forEach { point ->
                drawCircle(
                    color = color,
                    radius = 4.dp.toPx(),
                    center = point
                )
            }
        }

        // Rótulos dos dias
        val textPaint = Paint().asFrameworkPaint().apply {
            color = Color.DarkGray.toArgb()
            textSize = 12.sp.toPx()
            textAlign = android.graphics.Paint.Align.CENTER
        }

        dayLabels.forEachIndexed { index, day ->
            val x = paddingStart + (index * itemWidth)
            drawContext.canvas.nativeCanvas.drawText(
                day.take(3).uppercase(),
                x,
                size.height - 8.dp.toPx(),
                textPaint
            )
        }

        // Legenda
        val legendPaint = Paint().asFrameworkPaint().apply {
            textSize = 14.sp.toPx()
            textAlign = android.graphics.Paint.Align.LEFT
        }

        listOf(
            Triple(CoughingColor, "Tosse", 16.dp),
            Triple(SneezingColor, "Espirro", 32.dp),
            Triple(OtherColor, "Outros", 48.dp)
        ).forEach { (color, text, offset) ->
            legendPaint.color = color.toArgb()
            drawContext.canvas.nativeCanvas.drawText(
                text,
                paddingStart,
                offset.toPx(),
                legendPaint
            )
        }
    }
}

        