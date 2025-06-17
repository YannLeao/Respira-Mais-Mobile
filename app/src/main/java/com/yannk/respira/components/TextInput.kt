package com.yannk.respira.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable
fun TextInput( label : String) {
    Box {
        var texto by remember { mutableStateOf("") }
        var isFocused by remember { mutableStateOf(false) }


        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 20.dp, right = 20.dp, top = 5.dp, bottom = 5.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Magenta,
                unfocusedBorderColor = Color(0xFF2454B2),

            ),
            shape = if (isFocused) {
                RoundedCornerShape(12.dp)
            } else {
                RoundedCornerShape(20.dp)
            }
        )
    }
}