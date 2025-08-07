package com.yannk.respira.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yannk.respira.ui.theme.AzulMedio
import com.yannk.respira.ui.theme.LilasSuave


@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val errorType = ErrorType.fromMessage(message)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Column(
            modifier = modifier
                .width(280.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = errorType.icon,
                contentDescription = null,
                tint = errorType.color,
                modifier = Modifier.size(48.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = errorType.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = errorType.message,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.width(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = errorType.color,
                    contentColor = Color.White
                )
            ) {
                Text("OK")
            }
        }
    }
}

enum class ErrorType(
    val icon: ImageVector,
    val color: Color,
    val title: String,
    val message: String
) {
    NETWORK(
        icon = Icons.Filled.WifiOff,
        color = Color(0xFFE53935),
        title = "Sem conexão",
        message = "Verifique sua internet e tente novamente."
    ),
    CREDENTIALS(
        icon = Icons.Filled.Lock,
        color = Color(0xFFFFA000),
        title = "Credenciais inválidas",
        message = "E-mail ou senha incorretos."
    ),
    SERVER(
        icon = Icons.Filled.CloudOff,
        color = AzulMedio,
        title = "Erro no servidor",
        message = "Estamos com problemas técnicos. Tente mais tarde."
    ),
    GENERIC(
        icon = Icons.Filled.Error,
        color = LilasSuave,
        title = "Ocorreu um erro",
        message = "Tente novamente ou contate o suporte."
    );

    companion object {
        fun fromMessage(message: String?): ErrorType {
            return when {
                message.isNullOrEmpty() -> GENERIC
                message.contains("conexão", ignoreCase = true) -> NETWORK
                message.contains("credenciais", ignoreCase = true) -> CREDENTIALS
                message.contains("existe", ignoreCase = true) -> CREDENTIALS
                message.contains("servidor", ignoreCase = true) -> SERVER
                else -> GENERIC
            }
        }
    }
}

@Preview
@Composable
private fun ErrorPopUp() {
    ErrorDialog("---", {})
}
