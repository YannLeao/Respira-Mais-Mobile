package com.yannk.respira.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.yannk.respira.ui.components.DonutChart
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.PieChartItem

@Composable
fun DashboardHomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {

        FundoImg()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .background(Color.White)
                .align(Alignment.Center)
        ) {
            val data = listOf(
                PieChartItem(45f, Color(0xFFFF5252), "Heart Pts"),
                PieChartItem(30f, Color(0xFF4CAF50), "Steps"),
                PieChartItem(25f, Color(0xFF2196F3), "Sleep")
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DonutChart(
                    data = data,
                    radiusRatio = 0.5f
                )
            }
        }
    }
}