package com.yannk.respira.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yannk.respira.ui.theme.ButtonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onReload: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Transparent,
            actionIconContentColor = ButtonColor
        ),
        title = { Text("") },
        actions = {
            IconButton(onClick = onReload) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Recarregar"
                )
            }
        }
    )
}