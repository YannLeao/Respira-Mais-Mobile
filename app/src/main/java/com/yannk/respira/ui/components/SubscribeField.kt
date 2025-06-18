package com.yannk.respira.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yannk.respira.R

@Composable
fun SubscribeField(
    firstText: String,
    secondText: String,
    onClick: () -> Unit
    ){
    Column(modifier = Modifier.fillMaxWidth()
        .padding(top = 20.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )

            Text(
                text = firstText,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp),
                color = Color.Gray
            )

        }

        Row(modifier = Modifier.fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp,top = 10.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween){

            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black )) {
            Image( painter = painterResource(id = R.drawable.logo_google),
                contentDescription = "logo do google",
                modifier = Modifier.size(50.dp))}

            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black )) {
            Image(painter = painterResource(id = R.drawable.logo_x),
                contentDescription = "logo do X",
                modifier = Modifier.size(50.dp))}

            Button(onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Black )) {
            Image(painter = painterResource(id = R.drawable.logo_facebook),
                contentDescription = "logo do facebook",
                modifier = Modifier.size(50.dp))}
        }

        Button(onClick = onClick,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black )) {

                Text(
            text = secondText,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
    }
}