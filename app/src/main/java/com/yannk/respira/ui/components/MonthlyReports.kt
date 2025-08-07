package com.yannk.respira.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.theme.AzulClaro
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.ButtonColor

@Composable
fun MonthlyReports() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Relatório Mensal",
            style = MaterialTheme.typography.titleLarge,
            color = ButtonColor
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(31) { day ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .background(
                            color = when ((day % 3)) {
                                0 -> AzulClaro.copy(alpha = 0.3f)
                                1 -> AzulMedio.copy(alpha = 0.3f)
                                else -> ButtonColor.copy(alpha = 0.3f)
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("${day + 1}")
                }
            }
        }
    }
}