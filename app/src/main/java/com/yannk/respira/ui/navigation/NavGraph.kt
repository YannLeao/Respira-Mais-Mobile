package com.yannk.respira.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yannk.respira.ui.screens.DashboardHomeScreen
import com.yannk.respira.ui.screens.LoginScreen
import com.yannk.respira.ui.screens.MicrophoneScreen
import com.yannk.respira.ui.screens.SignInScreen
import com.yannk.respira.ui.screens.WelcomeScreen


object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val SIGN_IN = "sig_in"
    const val DASHBOARD_HOME = "dashboard_home"
    const val MICROPHONE = "microphone"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        fun NavGraphBuilder.defaultComposable(
            route: String,
            arguments: List<NamedNavArgument> = emptyList(),
            deepLinks: List<NavDeepLink> = emptyList(),
            content: @Composable (NavBackStackEntry) -> Unit
        ) {
            composable(
                route = route,
                arguments = arguments,
                deepLinks = deepLinks,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300))
                }
            ) { backStackEntry ->
                content(backStackEntry)
            }
        }

        defaultComposable(Routes.WELCOME) { WelcomeScreen(navController) }
        defaultComposable(Routes.LOGIN) { LoginScreen(navController) }
        defaultComposable(Routes.SIGN_IN) { SignInScreen(navController) }
        defaultComposable(Routes.MICROPHONE) { MicrophoneScreen(navController) }
        defaultComposable(Routes.DASHBOARD_HOME) { DashboardHomeScreen(navController) }
    }
}
