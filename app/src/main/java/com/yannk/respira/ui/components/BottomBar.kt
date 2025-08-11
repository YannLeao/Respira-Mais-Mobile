package com.yannk.respira.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.navigation.Routes

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {
    val itemColors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = MaterialTheme.colorScheme.primary,
        indicatorColor = MaterialTheme.colorScheme.primary
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,

    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            selected = currentRoute == Routes.PROFILE,
            onClick = {
                if (currentRoute != Routes.PROFILE) {
                    navController.navigate(Routes.PROFILE)
                }
            },
            colors = itemColors
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") },
            selected = currentRoute == Routes.DASHBOARD_HOME,
            onClick = {
                if (currentRoute != Routes.DASHBOARD_HOME) {
                    navController.navigate(Routes.DASHBOARD_HOME) {
                        popUpTo(Routes.DASHBOARD_HOME) { inclusive = true }
                    }
                }
            },
            colors = itemColors
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Relatórios") },
            selected = currentRoute == Routes.REPORTS,
            onClick = {
                if (currentRoute != Routes.REPORTS) {
                    navController.navigate(Routes.REPORTS)
                }
            },
            colors = itemColors
        )
    }
}

@Preview
@Composable
private fun BottomBarPrev() {
    BottomBar(
        navController = rememberNavController(),
        currentRoute = Routes.DASHBOARD_HOME
    )

}