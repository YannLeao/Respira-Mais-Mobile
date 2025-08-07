package com.yannk.respira.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.yannk.respira.ui.components.BottomBar
import com.yannk.respira.ui.navigation.Routes
import com.yannk.respira.ui.theme.AzulClaro
import com.yannk.respira.ui.theme.ButtonColor
import com.yannk.respira.ui.theme.TextColor
import com.yannk.respira.ui.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: UserViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUserName()
        viewModel.fetchUserEmail()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = ButtonColor
                )
            )
        },
        bottomBar = {
            BottomBar(
                navController = navController,
                currentRoute = Routes.PROFILE
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(AzulClaro.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Foto do perfil",
                    tint = ButtonColor,
                    modifier = Modifier.size(60.dp)
                )
            }

            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                color = ButtonColor
            )

            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = TextColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SettingsItem(
                    icon = Icons.Default.Settings,
                    text = "Configurações da Conta",
                    onClick = {
                        navController.navigate(Routes.SETTINGS) {
                            popUpTo(Routes.PROFILE) { inclusive = true }
                        }
                    }
                )

                SettingsItem(
                    icon = Icons.Default.Info,
                    text = "Sobre o Aplicativo",
                    onClick = {
                        navController.navigate(Routes.ABOUT) {
                            popUpTo(Routes.PROFILE) { inclusive = true }
                        }
                    }
                )

                SettingsItem(
                    icon = Icons.Default.Logout,
                    text = "Sair",
                    onClick = {
                        viewModel.logout()
                        navController.navigate(Routes.WELCOME) {
                            popUpTo(Routes.WELCOME) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = ButtonColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ButtonColor
            )
            Text(text)
        }
    }
}