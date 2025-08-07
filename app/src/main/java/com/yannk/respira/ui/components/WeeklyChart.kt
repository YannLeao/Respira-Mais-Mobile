// WeeklyChart.kt
package com.yannk.respira.ui.components // Mude para o seu pacote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun WeeklyChart(
    data: List<Pair<String, Float>>,
    modifier: Modifier = Modifier,
    chartHeight: Dp = 200.dp
) {
    // Se não houver dados, não renderiza nada.
    if (data.isEmpty()) {
        Box(
            modifier = modifier
                .height(chartHeight)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Sem dados para exibir")
        }
        return
    }

    val maxValue = data.maxOfOrNull { it.second } ?: 1f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(chartHeight + 40.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        // 2. Itera sobre cada item da lista de dados para criar uma barra.
        data.forEach { reportData ->

            // 3. Calcula a altura da barra proporcionalmente ao valor máximo.
            val barHeight = (reportData.second / maxValue) * chartHeight.value

            // Chama o Composable que desenha a barra individualmente
            ChartBar(
                day = reportData.first,
                height = barHeight.dp
            )
        }
    }
}

/**
 * Composable interno que representa uma única barra e seu rótulo.
 */
@Composable
private fun ChartBar(
    day: String,
    height: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // A barra visual
        Box(
            modifier = Modifier
                .width(35.dp)
                .height(height)
                .clip(RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                .background(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // O rótulo do dia
        Text(
            text = day,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
    }
}


// --- Preview para Visualização no Android Studio ---

@Preview(showBackground = true, widthDp = 380)
@Composable
fun WeeklyChartPreview() {
    val sampleData: List<Pair<String, Float>> = listOf(
        Pair("Seg", 150f),
        Pair("Ter", 280f),
        Pair("Qua", 90f),
        Pair("Qui", 210f),
        Pair("Sex", 180f),
        Pair("Sáb", 300f),
        Pair("Dom", 120f)
    )

    // Use um tema se tiver, ou um Surface para definir um fundo.
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            WeeklyChart(data = sampleData)
        }
    }
}

@Preview(showBackground = true, widthDp = 380)
@Composable
fun WeeklyChartEmptyPreview() {
    MaterialTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            WeeklyChart(data = emptyList())
        }
    }
}