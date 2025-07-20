package com.yannk.respira.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yannk.respira.ui.screens.LoginScreen
import com.yannk.respira.ui.screens.MicrophoneScreen
import com.yannk.respira.ui.screens.SigninScreen
import com.yannk.respira.ui.screens.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val SIG_IN = "sigin"
    const val HOME = "home"
    const val MICROPHONE = "microphone"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.WELCOME
    ) {
        composable(Routes.WELCOME) {
            WelcomeScreen(navController)
        }

        composable(Routes.LOGIN) {
            LoginScreen(navController)
        }

        composable(Routes.SIG_IN) {
            SigninScreen(navController)
        }

        composable(Routes.MICROPHONE) {
            MicrophoneScreen(navController)
        }
    }
}