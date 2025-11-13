package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaTermoDeUso(navController: NavController?) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Termos de Uso",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = { navController?.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Termos de Uso",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "1. Aceitação dos Termos\n\n" +
                    "Ao acessar e usar o INFOHUB, você concorda em cumprir e estar vinculado aos seguintes termos e condições...",
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController?.navigateUp() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Aceitar e Continuar")
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TelaTermoDeUso() {
    InfoHub_telasTheme {
        TelaTermoDeUso(null)
    }
}