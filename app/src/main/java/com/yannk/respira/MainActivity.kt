package com.yannk.respira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yannk.respira.ui.theme.RespiraTheme

//@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RespiraTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    InicioScreen(modifier = Modifier.padding(innerPadding))
//                }
            }
        }
    }
}
