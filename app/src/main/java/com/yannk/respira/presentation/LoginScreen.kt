package com.yannk.respira.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.R
import com.yannk.respira.components.BigButton
import com.yannk.respira.components.ButtonsLogin
import com.yannk.respira.components.FundoImg
import com.yannk.respira.components.SubscribeField
import com.yannk.respira.components.TextInput
import com.yannk.respira.components.VectorImg

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        FundoImg()
        VectorImg(
            modifier = Modifier.align(Alignment.BottomEnd),
            source = R.drawable.vector__1_
        )

        Column (modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally){

            ButtonsLogin(
            modifier = Modifier.padding(top = 240.dp)

        )

        Spacer(modifier = Modifier.height(32.dp))

            TextInput("Nome Completo")
            TextInput("Email")
            TextInput("Senha")
            Spacer(modifier = Modifier.height(32.dp))

            BigButton("Sign-in")

            SubscribeField()

        }
    }

}