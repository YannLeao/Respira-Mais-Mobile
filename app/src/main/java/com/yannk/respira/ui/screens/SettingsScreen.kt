package com.yannk.respira.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.components.BottomBar
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = ButtonColor
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = ButtonColor
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = Routes.SETTINGS
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SettingsSection(title = "Conta") {
                SettingsItem(
                    icon = Icons.Default.Person,
                    text = "Editar Perfil",
                    onClick = { navController.navigate(Routes.PROFILE) }
                )

                SettingsItem(
                    icon = Icons.Default.Lock,
                    text = "Alterar Senha",
                    onClick = { /* Navegar para tela de senha */ }
                )
            }

            SettingsSection(title = "Notificações") {
                var notificationsEnabled by remember { mutableStateOf(false) }
                SettingsSwitchItem(
                    icon = Icons.Default.Notifications,
                    text = "Notificações",
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }

            SettingsSection(title = "Aparência") {
                var darkModeEnabled by remember { mutableStateOf(false) }
                SettingsSwitchItem(
                    icon = Icons.Default.DarkMode,
                    text = "Modo Escuro",
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            }

            SettingsSection(title = "Sobre") {
                SettingsItem(
                    icon = Icons.Default.Info,
                    text = "Sobre o Aplicativo",
                    onClick = { navController.navigate(Routes.ABOUT) }
                )
            }
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = TextColor,
            modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ButtonColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, color = ButtonColor)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ButtonColor,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Gray
            )
        )
    }
}

@Preview
@Composable
private fun SettingsScreenPrev() {
    SettingsScreen(navController = rememberNavController())
}