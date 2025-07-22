package com.yannk.respira.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class PieChartItem(
    val value: Float,
    val color: Color,
    val label: String
)

@Composable
fun DonutChart(
    data: List<PieChartItem>,
    modifier: Modifier = Modifier,
    radiusRatio: Float = 0.6f
) {
    val total = data.sumOf { it.value.toDouble() }.toFloat()

    Canvas(modifier = modifier.size(200.dp)) {
        var startAngle = -90f

        data.forEach { item ->
            val sweepAngle = (item.value / total) * 360f
            drawDonutSlice(
                color = item.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                radiusRatio = radiusRatio
            )
            startAngle += sweepAngle
        }

        drawCircle(
            color = Color.White,
            radius = size.minDimension / 2 * radiusRatio
        )
    }
}

private fun DrawScope.drawDonutSlice(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    radiusRatio: Float
) {
    val outerRadius = size.minDimension / 4.5f
    val innerRadius = outerRadius * radiusRatio

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = androidx.compose.ui.geometry.Offset(
            (size.width - outerRadius * 2) / 2,
            (size.height - outerRadius * 2) / 2
        ),
        size = androidx.compose.ui.geometry.Size(outerRadius * 2, outerRadius * 2),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = outerRadius - innerRadius)
    )
}
