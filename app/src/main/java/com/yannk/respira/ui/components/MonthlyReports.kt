package com.yannk.respira.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.theme.AzulClaro
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.CoughingColor
import com.yannk.respira.ui.theme.OtherColor
import com.yannk.respira.ui.theme.SneezingColor
import com.yannk.respira.ui.theme.SnoringColor

@Composable
fun MonthlyReports() {
    var selectedDay by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Relatório Mensal",
            style = MaterialTheme.typography.titleLarge,
            color = ButtonColor
        )
        selectedDay?.let { day ->

            Text(
                text = "Dados do Dia $day",
                style = MaterialTheme.typography.titleMedium,
                color = ButtonColor,
                modifier = Modifier.padding(top = 8.dp)
            )

            val chartData = getChartDataForDay(day)

            DonutChart(data = chartData, legendOnSide = true, chartSize = 130.dp)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(31) { dayIndex ->
                val dayNumber = dayIndex + 1
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .clip(CircleShape)

                        .clickable {
                            selectedDay = dayNumber
                        }
                        .background(
                            color = when ((dayIndex % 3)) {
                                0 -> AzulClaro.copy(alpha = 0.3f)
                                1 -> AzulMedio.copy(alpha = 0.3f)
                                else -> ButtonColor.copy(alpha = 0.3f)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$dayNumber")
                }
            }
        }


    }
}


private fun getChartDataForDay(day: Int): List<AudioStat> {
    return when (day % 3) {
        1 -> listOf(
            AudioStat("Espirro", 40f, SneezingColor),
            AudioStat("Tosse", 20f, CoughingColor),
            AudioStat("Ronco", 30f, SnoringColor),
            AudioStat("Outros", 10f, OtherColor)
        )
        2 -> listOf(
            AudioStat("Espirro", 10f, SneezingColor),
            AudioStat("Tosse", 60f, CoughingColor),
            AudioStat("Ronco", 15f, SnoringColor),
            AudioStat("Outros", 15f, OtherColor)
        )
        else -> listOf(
            AudioStat("Espirro", 5f, SneezingColor),
            AudioStat("Tosse", 15f, CoughingColor),
            AudioStat("Ronco", 70f, SnoringColor),
            AudioStat("Outros", 10f, OtherColor)
        )
    }
}