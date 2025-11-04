package com.example.infohub_telas.telas

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfilPreview(navController: NavController
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Perfil Preview",
                navigationIcon = Icons.Default.ArrowBack,
                onNavigationIconClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Preview content here
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TelaPerfilPreviewPreview() {
    MaterialTheme {
        TelaPerfilPreview(
            navController = androidx.navigation.compose.rememberNavController(),
            
            
        )
    }
}
