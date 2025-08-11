//package com.yannk.respira.ui.theme
//
//import androidx.compose.ui.graphics.Color
//
//// Cores do app
//val ButtonColor = Color(0xFF2454B2)
//val TextColor = Color(0xFF4E87F6)
//
//// Cores do Gráfico
//val SneezingColor = Color(0xFF4E87F6)
//val CoughingColor = Color(0xFF2454B2)
//val SnoringColor = Color(0xFF1D3F91)
//val OtherColor = Color(0xFFD3D3D3)
//
//// Degradê
//val LilasSuave = Color(0xFF8B9CEB)
//val AzulMedio = Color(0xFF607FE5)
//val AzulClaro = Color(0xFFAAC4F8)
//val RoxoClaro = Color(0xFFBFB1F1)
//
//// Cores do tema escuro
//val Purple80 = Color(0xFFD0BCFF)
//val PurpleGrey80 = Color(0xFFCCC2DC)
//val Pink80 = Color(0xFFEFB8C8)
//
//val Purple40 = Color(0xFF6650a4)
//val PurpleGrey40 = Color(0xFF625b71)
//val Pink40 = Color(0xFF7D5260)

package com.yannk.respira.ui.theme

import androidx.compose.ui.graphics.Color

// ======================================================
// PALETA DE CORES PRINCIPAL - BASEADA NAS SUAS CORES
// ======================================================

// Cores para o Tema Claro
val PrimaryLight = Color(0xFF2454B2)      // Seu antigo ButtonColor, a cor principal da marca
val OnPrimaryLight = Color.White         // Cor do texto/ícone sobre a cor primária
val SecondaryLight = Color(0xFF4E87F6)    // Seu antigo TextColor, uma cor de destaque
val OnSecondaryLight = Color.White       // Cor do texto/ícone sobre a cor secundária
val BackgroundLight = Color(0xFFF7F9FF)  // Um branco levemente azulado, mais suave que o branco puro
val OnBackgroundLight = Color(0xFF1A1C1E) // Cor do texto principal
val SurfaceLight = Color.White           // Cor de fundo para Cards, AppBars, etc.
val OnSurfaceLight = Color(0xFF1A1C1E)    // Cor do texto sobre o Surface

// Cores para o Tema Escuro (Sugestão baseada na sua paleta de degradê)
val PrimaryDark = Color(0xFFAAC4F8)       // Seu AzulClaro, se destaca bem no escuro
val OnPrimaryDark = Color(0xFF002F64)     // Um azul bem escuro para texto/ícone sobre PrimaryDark
val SecondaryDark = Color(0xFFBFB1F1)     // Seu RoxoClaro, como cor de destaque
val OnSecondaryDark = Color(0xFF2E1E5F)    // Um roxo escuro para texto/ícone
val BackgroundDark = Color(0xFF121212)    // Fundo padrão para modo escuro
val OnBackgroundDark = Color(0xFFE2E6EB)  // Cor do texto principal (claro)
val SurfaceDark = Color(0xFF1E1E1E)       // Cor de fundo para Cards, AppBars, etc.
val OnSurfaceDark = Color(0xFFE2E6EB)     // Cor do texto sobre o Surface

// ======================================================
// CORES ESPECÍFICAS QUE NÃO MUDAM COM O TEMA
// ======================================================

val SneezingColor = Color(0xFF4E87F6)
val CoughingColor = Color(0xFF2454B2)
val SnoringColor = Color(0xFF1D3F91)
val OtherColor = Color(0xFFD3D3D3)