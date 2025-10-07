package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaChatDePrecos (navController: NavController?){

    val mensagens = remember { mutableStateOf("Olá! Em que posso te ajudar?") }
    var usuarioMensagem by remember { mutableStateOf("") }
    val chat = RetrofitFactory.create()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6))
    )
}



























@Preview(showSystemUi = true)
@Composable
fun TelaChatDePrecosPreview() {
    InfoHub_telasTheme {
        TelaChatDePrecos(null)
    }
}