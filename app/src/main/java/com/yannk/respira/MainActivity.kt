package com.yannk.respira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.yannk.respira.ui.navigation.NavGraph
import com.yannk.respira.ui.theme.RespiraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RespiraTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}