package com.yannk.respira.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor

@Composable
fun TextInput(
    label : String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    Box {
        var isFocused by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier
                .fillMaxWidth()
                .absolutePadding(left = 20.dp, right = 20.dp, top = 5.dp, bottom = 5.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = ButtonColor,
                unfocusedTextColor = ButtonColor,
                focusedBorderColor = TextColor,
                unfocusedBorderColor = ButtonColor,
                focusedLabelColor = TextColor,
                unfocusedLabelColor = ButtonColor,
                cursorColor = TextColor,
            ),

            shape = if (isFocused) {
                RoundedCornerShape(12.dp)
            } else {
                RoundedCornerShape(20.dp)
            },

            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },

            keyboardOptions = if (isPassword) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false,
                    capitalization = KeyboardCapitalization.None
                )
            } else {
                KeyboardOptions.Default
            }
        )
    }
}