package com.yannk.respira.ui.components.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun BigButton(
    text : String,
    onClick: () -> Unit,
    enabled: Boolean
){
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.width(246.dp)
            .height(54.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) MaterialTheme.colorScheme.primary else Color.White,
            contentColor = if (enabled) Color.White else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text, fontWeight = FontWeight.Medium)
    }
}