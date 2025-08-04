package com.yannk.respira.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.yannk.respira.ui.components.ErrorDialog
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.LoadingDialog
import com.yannk.respira.ui.components.SubscribeField
import com.yannk.respira.ui.components.TextInput
import com.yannk.respira.ui.components.VectorImg
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.viewmodel.UserViewModel
import com.yannk.respira.util.ResultState

@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
    ) {

    val state = viewModel.registerState.collectAsState().value

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val isEnabled = name.isNotBlank() && email.isNotBlank() && password.isNotBlank()

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
                navController = navController
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextInput(label = "Nome Completo", value = name, onValueChange = { name = it } )
            TextInput(label = "Email", value = email, onValueChange = { email = it })
            TextInput(label = "Senha", isPassword = true, value = password, onValueChange = { password = it })
            Spacer(modifier = Modifier.height(20.dp))

            BigButton(
                text = "Sign-in",
                enabled = isEnabled,
                onClick = {
                    viewModel.register(name, email, password)
                }
            )

            SubscribeField(
                firstText = "Cadastre-se com",
                secondText = "Já possui uma conta? Sign-up",
                onClick = {
                    navController.navigate(Routes.LOGIN) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Routes.SIGN_IN) { saveState = true }
                    }
                }
            )
        }

        when (state) {
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
                ErrorDialog(
                    message = state.message,
                    onDismiss = { viewModel.clearRegisterState() }
                )
            }

            null -> Unit
        }

    }
}

@Preview
@Composable
private fun SigIn() {
    SignInScreen(navController = rememberNavController())
}