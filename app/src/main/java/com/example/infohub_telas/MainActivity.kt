package com.example.infohub_telas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.example.infohub_telas.telas.TelaLoginScreen
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.util.AppContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppContext.initialize(this)
        enableEdgeToEdge()
        setContent {
            InfoHub_telasTheme {
                Navigator(TelaLoginScreen())
            }
        }
    }
}
