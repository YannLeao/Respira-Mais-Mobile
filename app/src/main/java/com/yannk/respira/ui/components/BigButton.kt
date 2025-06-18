package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.theme.ButtonColor

@Composable
fun BigButton(
    text : String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = Modifier.width(246.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor,
            contentColor = Color.White
        )
    ) {
        Text(text, fontWeight = FontWeight.Medium)
    }
}