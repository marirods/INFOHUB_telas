package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerarRelatorioScreen(navController: NavController
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerar Relatório",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Relatório content here
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GerarRelatorioScreenPreview() {
    MaterialTheme {
        GerarRelatorioScreen(
            navController = rememberNavController()
        )
    }
}
