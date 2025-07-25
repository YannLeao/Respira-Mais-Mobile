package com.yannk.respira.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.R
import com.yannk.respira.ui.components.BigButton
import com.yannk.respira.ui.components.ButtonsLogin
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.SubscribeField
import com.yannk.respira.ui.components.TextInput
import com.yannk.respira.ui.components.VectorImg
import com.yannk.respira.ui.navigation.Routes

@Composable
fun SigninScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        FundoImg()
        VectorImg(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            source = R.drawable.vector_sign
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonsLogin(
                modifier = Modifier.padding(top = 200.dp),
                isLogin = false,
                navController = navController  // Use o navController passado como parâmetro
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextInput("Nome Completo")
            TextInput("Email")
            TextInput("Senha")
            Spacer(modifier = Modifier.height(20.dp))

            BigButton(
                text = "Sign-in",
                onClick = {
                    navController.navigate(Routes.DASHBOARD_HOME) {
                        // Configurações recomendadas para navegação
                        launchSingleTop = true
                        popUpTo(Routes.SIGN_IN) { saveState = true }
                    }
                }
            )

            SubscribeField(
                firstText = "Cadastre-se com",
                secondText = "Já possui uma conta? Sign-up",
                onClick = {
                    navController.navigate(Routes.LOGIN) {
                        launchSingleTop = true
                        popUpTo(Routes.SIGN_IN) { saveState = true }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun SigIn() {
    SigninScreen(navController = rememberNavController())
}