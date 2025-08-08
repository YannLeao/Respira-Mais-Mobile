package com.yannk.respira.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.components.WeeklyChart
import com.yannk.respira.ui.theme.AzulClaro
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor
@Preview
@Composable
fun WeeklyReports() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Relatório Semanal",
            style = MaterialTheme.typography.titleLarge,
            color = ButtonColor
        )


        val weeklyData: List<Pair<String, Float>> = listOf(
            Pair("Seg", 150f),
            Pair("Ter", 280f),
            Pair("Qua", 90f),
            Pair("Qui", 210f),
            Pair("Sex", 180f),
            Pair("Sáb", 300f),
            Pair("Dom", 120f)
        )

        WeeklyChart(data = weeklyData)


        Column {
            MetricItem("Total de Registros", "24", ButtonColor)
            MetricItem("Média Diária", "3.4", AzulMedio)
        }
    }
}

@Composable
fun MetricItem(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = TextColor)
        Text(value, color = color, fontWeight = FontWeight.Bold)
    }
}
        