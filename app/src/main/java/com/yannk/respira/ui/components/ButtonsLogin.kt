package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.navigation.Routes
import androidx.navigation.NavHostController



@Composable
fun ButtonsLogin(modifier: Modifier, isLogin: Boolean, navController: NavHostController) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(95.dp)
            .absolutePadding(left = 40.dp, right = 35.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically


    ) {
        if (isLogin) {
            Button(
                onClick = {navController.navigate(Routes.SIGN_IN)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF4E87F6)
                )
            ) {
                Text(
                    "Sign-in",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White

                )
            }

            Button(
                onClick = {navController.navigate(Routes.LOGIN)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Log-in",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4E87F6)
                )
            }
        } else {
            Button(
                onClick = {navController.navigate(Routes.SIGN_IN)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Sign-in",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF4E87F6)
                )
            }

            Button(
                onClick = {navController.navigate(Routes.LOGIN)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF4E87F6)
                )
            ) {
                Text(
                    "Log-in",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

        }
    }
}
