package com.yannk.respira.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.ButtonColor

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = ButtonColor
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            selected = currentRoute == Routes.PROFILE,
            onClick = {
                if (currentRoute != Routes.PROFILE) {
                    navController.navigate(Routes.PROFILE)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = ButtonColor,
                indicatorColor = ButtonColor
            )
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
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = ButtonColor,
                indicatorColor = ButtonColor
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.BarChart, contentDescription = "Relatórios") },
            selected = currentRoute == Routes.REPORTS,
            onClick = {
                if (currentRoute != Routes.REPORTS) {
                    navController.navigate(Routes.REPORTS)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = ButtonColor,
                indicatorColor = ButtonColor
            )
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