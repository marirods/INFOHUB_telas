package com.example.infohub_telas.telas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.navigation.AppScreens
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHomeJuridico(
    onNavigateToGerenciamentoEmpresas: () -> Unit,
    onNavigateToProdutos: () -> Unit,
    onNavigateToRelatorios: () -> Unit,
    onNavigateToNovaEmpresa: () -> Unit,
    onNavigateToDocumentos: () -> Unit,
    onNavigateToConfiguracoes: () -> Unit,
    modifier: Modifier = Modifier
) {
    val menuItems = listOf(
        MenuItem(
            icon = Icons.Default.Business,
            title = "Gerenciar Empresas",
            color = Color(0xFF2196F3),
            onClick = onNavigateToGerenciamentoEmpresas
        ),
        MenuItem(
            icon = Icons.Default.Assessment,
            title = "Relatórios",
            color = Color(0xFF4CAF50),
            onClick = onNavigateToRelatorios
        ),
        MenuItem(
            icon = Icons.Default.ShoppingCart,
            title = "Produtos",
            color = Color(0xFF9C27B0),
            onClick = onNavigateToProdutos
        ),
        MenuItem(
            icon = Icons.Default.Add,
            title = "Nova Empresa",
            color = Color(0xFFF9A825),
            onClick = onNavigateToNovaEmpresa
        ),
        MenuItem(
            icon = Icons.Default.Description,
            title = "Documentos",
            color = Color(0xFF795548),
            onClick = onNavigateToDocumentos
        ),
        MenuItem(
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
                .verticalScroll(rememberScrollState())
        ) {
            // Welcome Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Bem-vindo ao Portal Jurídico",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Gerencie suas empresas e documentos em um só lugar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            }

            Text(
                "Menu Principal",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Menu Grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                menuItems.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        rowItems.forEach { menuItem ->
                            MenuCard(
                                menuItem = menuItem,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        // If odd number of items, add empty space to maintain grid
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuCard(
    menuItem: MenuItem,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = menuItem.onClick,
        modifier = modifier.height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = menuItem.color.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = menuItem.icon,
                contentDescription = menuItem.title,
                tint = menuItem.color,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
        }
    }
}

private data class MenuItem(
    val icon: ImageVector,
    val title: String,
    val color: Color,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun TelaHomeJuridicoPreview() {
    InfoHub_telasTheme {
        TelaHomeJuridico(
            onNavigateToGerenciamentoEmpresas = {},
            onNavigateToProdutos = {},
            onNavigateToRelatorios = {},
            onNavigateToNovaEmpresa = {},
            onNavigateToDocumentos = {},
            onNavigateToConfiguracoes = {}
        )
    }
}
