package com.yannk.respira.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.components.buttons.BottomBar
import com.yannk.respira.ui.components.dashboard.DashboardContent
import com.yannk.respira.ui.components.home.TopBar
import com.yannk.respira.ui.navigation.Routes

@Composable
fun DashboardScreen(
    navController: NavHostController
    //dashboardViewModel: DashboardViewModel = hiltViewModel()
    ) {

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                onReload = {/* scope.launch { dashboardViewModel.refreshData() }*/ },


            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = Routes.DASHBOARD_HOME
            )
        }
    ) { padding ->
        DashboardContent(
            modifier = Modifier.padding(padding)
        )
    }
}

@Preview
@Composable
private fun DashBoardPrev() {
    DashboardScreen(navController = rememberNavController())
}