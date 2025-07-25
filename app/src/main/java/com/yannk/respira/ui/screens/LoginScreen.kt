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
fun LoginScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        FundoImg()
        VectorImg(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(WindowInsets.navigationBars.asPaddingValues()),
            source = R.drawable.vector__3_
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
                isLogin = true,
                navController = navController  // Use o navController passado como parâmetro
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextInput("Email")
            Spacer(modifier = Modifier.height(10.dp))

            TextInput("Senha")
            Spacer(modifier = Modifier.height(42.dp))

            BigButton(
                text = "Sign-up",
                onClick = {
                    navController.navigate(Routes.DASHBOARD_HOME) {
                        launchSingleTop = true
                        // Corrigido: popUpTo deve referenciar a tela atual (LOGIN)
                        popUpTo(Routes.LOGIN) { saveState = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            SubscribeField(
                firstText = "Entre com",
                secondText = "Não possui uma conta? Cadastre-se",
                onClick = {
                    navController.navigate(Routes.SIGN_IN) {
                        launchSingleTop = true
                        popUpTo(Routes.LOGIN) { saveState = true }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun Login() {
    LoginScreen(navController = rememberNavController())
}