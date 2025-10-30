package com.example.infohub_telas.telas.juridico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoHomeScreen(
    onNavigateToGerenciamentoEmpresas: () -> Unit,
    onNavigateToProdutos: () -> Unit,
    onNavigateToRelatorios: () -> Unit,
    onNavigateToDocumentos: () -> Unit,
    onNavigateToConfiguracoes: () -> Unit,
    onNavigateToCadastroEmpresa: () -> Unit,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        JuridicoMenuItem(
            icon = Icons.Default.Business,
            title = "Gerenciar Empresas",
            color = Color(0xFF2196F3),
            onClick = onNavigateToGerenciamentoEmpresas
        ),
        JuridicoMenuItem(
            icon = Icons.Default.ShoppingCart,
            title = "Produtos e Serviços",
            color = Color(0xFF4CAF50),
            onClick = onNavigateToProdutos
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Add,
            title = "Nova Empresa",
            color = Color(0xFFF9A825),
            onClick = onNavigateToCadastroEmpresa
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Description,
            title = "Relatórios",
            color = Color(0xFF9C27B0),
            onClick = onNavigateToRelatorios
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Folder,
            title = "Documentos",
            color = Color(0xFF795548),
            onClick = onNavigateToDocumentos
        ),
        JuridicoMenuItem(
            icon = Icons.Default.Settings,
            title = "Configurações",
            color = Color(0xFF607D8B),
            onClick = onNavigateToConfiguracoes
        )
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Portal Jurídico",
                navigationIcon = null
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            WelcomeCard()

            Text(
                "Menu Principal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(menuItems) { item ->
                    MenuCard(item)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuCard(item: JuridicoMenuItem) {
    Card(
        onClick = { item.onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = item.color.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = item.color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1976D2)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Bem-vindo ao Portal Jurídico",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Gerencie suas empresas e processos jurídicos",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        }
    }
}

data class JuridicoMenuItem(
    val icon: ImageVector,
    val title: String,
    val color: Color,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun JuridicoHomeScreenPreview() {
    InfoHub_telasTheme {
        JuridicoHomeScreen(
            onNavigateToGerenciamentoEmpresas = {},
            onNavigateToProdutos = {},
            onNavigateToRelatorios = {},
            onNavigateToDocumentos = {},
            onNavigateToConfiguracoes = {},
            onNavigateToCadastroEmpresa = {}
        )
    }
}
