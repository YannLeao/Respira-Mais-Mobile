package com.yannk.respira.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.R
import com.yannk.respira.ui.components.BigButton
import com.yannk.respira.ui.components.ButtonsLogin
import com.yannk.respira.ui.components.FundoImg
import com.yannk.respira.ui.components.SubscribeField
import com.yannk.respira.ui.components.TextInput
import com.yannk.respira.ui.components.VectorImg

@Preview
@Composable
fun LoginScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        FundoImg()
        VectorImg(
            modifier = Modifier.align(Alignment.BottomEnd),
            source = R.drawable.vector__3_
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ButtonsLogin(
                modifier = Modifier.padding(top = 240.dp),
                isLogin = false
            )
            Spacer(modifier = Modifier.height(32.dp))

            TextInput("Email")
            Spacer(modifier = Modifier.height(10.dp))
            TextInput("Senha")
            Spacer(modifier = Modifier.height(42.dp))

            BigButton("Sign-up")
            Spacer(modifier = Modifier.height(32.dp))
            SubscribeField(firstText = "Entre com", secondText = "Não possui uma conta? Cadastre-se")

        }
    }
}