package com.example.infohub_telas.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.model.PromocaoProduto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerenciamentoEmpresasScreen(
    navController: NavController,
    estabelecimento: Estabelecimento,
    produtos: List<PromocaoProduto>,
    categoria: String,
    id: String,
    empresa: Empresa
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerenciamento de Empresas",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.navigateUp() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Content here
        }
    }
}
