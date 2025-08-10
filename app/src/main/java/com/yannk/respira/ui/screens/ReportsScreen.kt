package com.yannk.respira.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.components.BottomBar
import com.yannk.respira.ui.components.MonthlyReports
import com.yannk.respira.ui.components.TopBar
import com.yannk.respira.ui.components.WeeklyReports
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor
import com.yannk.respira.ui.viewmodel.ReportsViewModel

@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportsViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(ReportTab.WEEKLY) }

    Scaffold(
        topBar = { TopBar(
                onReload = { /*viewModel.refreshData()*/ }
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = Routes.REPORTS
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = Color.White,
                contentColor = ButtonColor,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.ordinal]),
                        height = 3.dp,
                        color = ButtonColor
                    )
                }
            )

            {
                ReportTab.entries.forEach { tab ->
                    Tab(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        text = {
                            Text(
                                tab.title,
                                color = if (selectedTab == tab) ButtonColor else TextColor
                            )
                        }
                    )
                }
            }

            when (selectedTab) {
                ReportTab.WEEKLY -> WeeklyReports()
                ReportTab.MONTHLY -> MonthlyReports()
            }
        }
    }
}

enum class ReportTab(val title: String) {
    WEEKLY("Semanal"),
    MONTHLY("Mensal")
}

@Preview
@Composable
private fun ReportsScreenPrev() {
    ReportsScreen(navController = rememberNavController())
}