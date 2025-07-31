package com.yannk.respira.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.R
import com.yannk.respira.ui.components.BigButton
import com.yannk.respira.ui.components.ButtonsLogin
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.LoadingDialog
import com.yannk.respira.ui.components.SimpleDialog
import com.yannk.respira.ui.components.SubscribeField
import com.yannk.respira.ui.components.TextInput
import com.yannk.respira.ui.components.VectorImg
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.viewmodel.UserViewModel
import com.yannk.respira.util.ResultState

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val loginState = viewModel.loginState.collectAsState().value

    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    val isEnabled = email.value.isNotBlank() && password.value.isNotBlank()

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
                navController = navController
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextInput(label = "Email", value = email.value, onValueChange = { email.value = it })
            Spacer(modifier = Modifier.height(10.dp))

            TextInput(label = "Senha", isPassword = true, value = password.value, onValueChange = { password.value = it })
            Spacer(modifier = Modifier.height(42.dp))

            BigButton(
                text = "Sign-up",
                enabled = isEnabled,
                onClick = {
                    viewModel.login(email.value, password.value)
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

        when (loginState) {
            is ResultState.Loading -> {
                LoadingDialog()
            }

            is ResultState.Success<*> -> {
                LaunchedEffect(Unit) {
                    viewModel.clearRegisterState()
                    navController.navigate(Routes.DASHBOARD_HOME) {
                        launchSingleTop = true
                        popUpTo(Routes.SIGN_IN) { inclusive = true }
                    }
                }
            }

            is ResultState.Error -> {
                SimpleDialog(
                    message = loginState.message,
                    onDismiss = { viewModel.clearLoginState() }
                )
            }

            null -> Unit
        }
    }
}

@Preview
@Composable
private fun Login() {
    LoginScreen(navController = rememberNavController())
}