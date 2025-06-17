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

@Preview(showBackground = true)
@Composable
fun SignInScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        FundoImg()
        VectorImg(
            modifier = Modifier.align(Alignment.BottomEnd),
            source = R.drawable.vector_sign
        )

        Column (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){

            ButtonsLogin(
            modifier = Modifier.padding(top = 240.dp),
                isLogin = true

        )

        Spacer(modifier = Modifier.height(30.dp))

            TextInput("Nome Completo")
            TextInput("Email")
            TextInput("Senha")
            Spacer(modifier = Modifier.height(30.dp))

            BigButton("Sign-in")

            SubscribeField(firstText = "Cadastre-se com", secondText = "Já possui uma conta? Sign-up")

        }
    }

}