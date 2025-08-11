package com.yannk.respira.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.components.BottomBar
import com.yannk.respira.ui.components.DashboardContent
import com.yannk.respira.ui.components.TopBar
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.viewmodel.DashboardViewModel
import kotlinx.coroutines.launch

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