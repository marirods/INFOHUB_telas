package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.EmpresaCard
import com.example.infohub_telas.components.MyTopAppBar
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilEmpresa(navController: NavController, empresa: Empresa) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Perfil da Empresa",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { navController.navigate("configuracaoPerfil") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Configurações", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0)) // Fundo cinza claro
        ) {
            // Banner azul
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFF1976D2))
            )

            // Card da empresa
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmpresaCard(empresa = empresa, onEditarClick = { navController.navigate("configuracaoPerfil") })
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilEmpresaPreview() {
    val empresaSample = Empresa(
        nome = "Tech Solutions Ltda",
        cnpj = "12.345.678/0001-99",
        email = "contato@techsolutions.com",
        telefone = "(11) 99999-9999",
        endereco = "Rua Exemplo, 123, São Paulo",
        setor = "Tecnologia",
        descricao = "Empresa de soluções tecnológicas.",
        logoUrl = ""
    )
    InfoHub_telasTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            PerfilEmpresa(navController = rememberNavController(), empresa = empresaSample)
        }
    }
}
